package edu.kit.checkstyle.checks;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import edu.kit.checkstyle.TokenSearcherCheck;


/**
 * Checks the complexity of single methods, where complexity is measured in the
 * number of method chains that are repeated.
 *
 * @since JDK1.7, 14.06.2013
 */
public class CallHierarchyComplexityCheck extends TokenSearcherCheck {

  @Override
  public int[] getDefaultTokens() {
    return new int[] {
      TokenTypes.METHOD_DEF
    };
  }

  @Override
  public void visitToken(final DetailAST ast) {
    try {
    final DetailAST body = ast.findFirstToken(TokenTypes.SLIST);
    final List<Set<DetailAST>> chains = findAllMethodChains(body);
    if (!chains.isEmpty()) {
      final List<Set<DetailAST>> duplicates = findDuplicates(chains.remove(0), chains);
      for (final Set<DetailAST> duplicate : duplicates) {
        final DetailAST errorAst = duplicate.iterator().next();
        log(errorAst.getLineNo(), errorAst.getColumnNo(), "duplicate found");
      }
    }

    } catch(final Exception e) {
      e.printStackTrace();
    }
  }

  private List<Set<DetailAST>> findDuplicates(final Set<DetailAST> chain, final List<Set<DetailAST>> chains) {
    final Set<String> names = namesOf(chain);
    final List<Set<DetailAST>> duplicates = mkList();
    for (final Set<DetailAST> curChain : chains) {
      final Set<String> intersection = Sets.intersection(names, namesOf(curChain));
      if (!intersection.isEmpty()) {
        duplicates.add(curChain);
      }
    }
    return duplicates;
  }

  /**
   * Helper function
   *
   * chain map (_.getText)
   */
  private Set<String> namesOf(final Set<DetailAST> chain) {
    final Set<String> names = mkSet();
    for (final DetailAST ast : chain) {
      names.add(ast.getText());
    }
    return names;
  }

  private List<Set<DetailAST>> findAllMethodChains(final DetailAST ast) {
    final List<Set<DetailAST>> chains = mkList();
    for (DetailAST child = ast.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child.getType() == TokenTypes.METHOD_CALL) {
        final Set<DetailAST> chain = findMethodChain(child);
        if (!chain.isEmpty()) {
          chains.add(chain);
        }
      } else {
        chains.addAll(findAllMethodChains(child));
      }
    }
    return chains;
  }

  private Set<DetailAST> findMethodChain(final DetailAST ast) {
    require(ast.getType() == TokenTypes.METHOD_CALL, "AST is not a method call");

    final DetailAST dot = ast.findFirstToken(TokenTypes.DOT);
    return dot != null ? nextMethodChain(dot) : this.<DetailAST>mkSet();
  }

  /**
   * Helper function of {@link this#findMethodChain(DetailAST)}.
   */
  private Set<DetailAST> nextMethodChain(final DetailAST dot) {
    require(dot.getType() == TokenTypes.DOT, "AST is not a dot");

    final DetailAST lhs = dot.getFirstChild();
    final DetailAST rhs = dot.getLastChild();

    if (lhs.getType() == TokenTypes.IDENT) {
      return mkSet(lhs, rhs);
    } else {
      final DetailAST nextDot = lhs.findFirstToken(TokenTypes.DOT);

      final Set<DetailAST> nextIdents = nextDot == null ?
          mkSet(lhs.findFirstToken(TokenTypes.IDENT)) :
          nextMethodChain(nextDot);
      return Sets.union(nextIdents, mkSet(rhs));
    }
  }
}
