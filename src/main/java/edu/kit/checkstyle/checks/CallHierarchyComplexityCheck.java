package edu.kit.checkstyle.checks;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import edu.kit.checkstyle.CollectionUtils;
import edu.kit.checkstyle.TokenSearcherCheck;
import static edu.kit.checkstyle.CollectionUtils.*;


/**
 * Checks the complexity of single methods, where complexity is measured in the
 * number of method chains that are repeated.
 *
 * @since JDK1.7, 14.06.2013
 */
public class CallHierarchyComplexityCheck extends TokenSearcherCheck {

  static abstract class Ref {
    public final String name;

    public Ref(final String name) {
      this.name = name;
    }

    abstract boolean isMethod();

    @Override
    public int hashCode() {
      return name.hashCode() + (isMethod() ? 1231 : 1237) * 31;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj instanceof Ref) {
        final Ref ref = (Ref) obj;
        return isMethod() == ref.isMethod() && name.equals(ref.name);
      }
      return false;
    }

    @Override
    public String toString() {
      return name;
    }
  }
  static class MethodRef extends Ref {

    public MethodRef(final String name) {
      super(name);
    }

    @Override
    public boolean isMethod() {
      return true;
    }
  }
  static class VariableRef extends Ref {

    public VariableRef(final String name) {
      super(name);
    }

    @Override
    public boolean isMethod() {
      return false;
    }
  }

  @Override
  public int[] getDefaultTokens() {
    return new int[] {
      TokenTypes.METHOD_DEF
    };
  }

  static class SymbolTable {

    final Map<Ref, Set<DetailAST>> table = mkMap();

    void addSymbol(final Ref ref, final DetailAST ast) {
      if (table.containsKey(ref)) {
        table.get(ref).add(ast);
      } else {
        table.put(ref, mkSet(ast));
      }
    }

    void addSymbol(final Ref ref) {
      if (table.containsKey(ref)) {
        throw new UnsupportedOperationException("symbol table already contains '" + ref + "'");
      }
      table.put(ref, CollectionUtils.<DetailAST>mkSet());
    }

    void init() {
      table.clear();
    }

    @Override
    public String toString() {
      return "SymbolTable" + table;
    }
  }

  private static final Ref THIS_REF = new VariableRef("this");

  private final SymbolTable symbolTable = new SymbolTable();

  @Override
  public void visitToken(final DetailAST ast) {
    try {
    symbolTable.init();

    final DetailAST body = ast.findFirstToken(TokenTypes.SLIST);

    findAllMethodChains(body);
    findDuplicates();
    } catch(final Exception e) {
      e.printStackTrace();
    }
  }

  private void findDuplicates() {
    Set<DetailAST> duplicates = mkSet();
    for (final Map.Entry<Ref, Set<DetailAST>> p : symbolTable.table.entrySet()) {
      final Set<DetailAST> occurrences = p.getValue();
      if (occurrences.size() > 1 && !p.getKey().equals(THIS_REF)) {
        duplicates = Sets.union(duplicates, occurrences);
      }
    }
    final Set<DetailAST> lineStarts = symbolTable.table.get(THIS_REF);

    for (final DetailAST ast : differentLineNo(duplicates)) {
      final DetailAST start = find(lineStarts, ast.getLineNo());
      log(start.getLineNo(), start.getColumnNo(), "duplicate found");
    }
  }

  private DetailAST find(final Set<DetailAST> set, final int lineNo) {
    for (final DetailAST ast : set) {
      if (ast.getLineNo() == lineNo) {
        return ast;
      }
    }
    throw new IllegalArgumentException("line number '" + lineNo + "' not found");
  }

  private Set<DetailAST> differentLineNo(final Set<DetailAST> set) {
    if (set.isEmpty()) {
      return set;
    }
    final List<DetailAST> elems = mkList();
    elems.addAll(set);
    Collections.sort(elems, new Comparator<DetailAST>() {
      @Override
      public int compare(final DetailAST a1, final DetailAST a2) {
        return a1.getColumnNo() - a2.getColumnNo();
      }
    });
    final DetailAST first = elems.get(0);
    final Set<DetailAST> ret = new TreeSet<>(new Comparator<DetailAST>() {
      @Override
      public int compare(final DetailAST a1, final DetailAST a2) {
        return a1.getLineNo() - a2.getLineNo();
      }
    });
    ret.add(first);
    for (int i = 1, end = elems.size(); i < end; ++i) {
      if (first.getLineNo() != elems.get(i).getLineNo()) {
        ret.add(elems.get(i));
      }
    }
    return ret;
  }

  private void findAllMethodChains(final DetailAST ast) {
    for (DetailAST child = ast.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child.getType() == TokenTypes.METHOD_CALL) {
        findMethodChain(child);
      } else {
        findAllMethodChains(child);
      }
    }
  }

  private void findMethodChain(final DetailAST ast) {
    require(ast.getType() == TokenTypes.METHOD_CALL, "AST is not a method call");

    final DetailAST dot = ast.findFirstToken(TokenTypes.DOT);
    if (dot == null) {
      final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
      symbolTable.addSymbol(THIS_REF, ident);
    } else {
      nextMethodChain(dot, null);
    }
  }

  /**
   * Helper function of {@link this#findMethodChain(DetailAST)}.
   */
  private void nextMethodChain(final DetailAST dot, final DetailAST parent) {
    require(dot.getType() == TokenTypes.DOT, "AST is not a dot");

    final DetailAST lhs = dot.getFirstChild();
    final DetailAST rhs = dot.getLastChild();

    if (lhs.getType() == TokenTypes.IDENT) {
      symbolTable.addSymbol(THIS_REF, lhs);
      symbolTable.addSymbol(new VariableRef(lhs.getText()), rhs);
      // TODO what if rhs is variable?
      symbolTable.addSymbol(new MethodRef(rhs.getText()), parent);
    } else {
      final DetailAST nextDot = lhs.findFirstToken(TokenTypes.DOT);

      if (nextDot == null) {
        final DetailAST ident = lhs.findFirstToken(TokenTypes.IDENT);
        symbolTable.addSymbol(THIS_REF, ident);
        symbolTable.addSymbol(new MethodRef(ident.getText()), rhs);
        symbolTable.addSymbol(new MethodRef(rhs.getText()), parent);
      } else {
        symbolTable.addSymbol(new MethodRef(rhs.getText()), parent);
        nextMethodChain(nextDot, rhs);
      }
    }
  }
}
