package edu.kit.checkstyle.checks;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


/**
 * Checks for the existence of instanceof keywords. It these keywords are found
 * outside of an equals method an error is reported.
 *
 * @since JDK1.7, Jun 3, 2013
 */
public class InstanceofUsageCheck extends Check {

  @Override
  public int[] getDefaultTokens() {
    return new int[] {
        TokenTypes.LITERAL_INSTANCEOF
    };
  }

  @Override
  public void visitToken(final DetailAST ast) {
    final int line = ast.getLineNo();
    final int column = ast.getColumnNo();

    DetailAST methodDef = ast.getParent();
    while (methodDef.getType() != TokenTypes.METHOD_DEF) {
      methodDef = methodDef.getParent();
    }

    final DetailAST methodName = methodDef.findFirstToken(TokenTypes.IDENT);
    final DetailAST methodType = methodDef.findFirstToken(TokenTypes.TYPE);
    final DetailAST params = methodDef.findFirstToken(TokenTypes.PARAMETERS);
    final DetailAST paramType = params.findFirstToken(TokenTypes.PARAMETER_DEF).findFirstToken(TokenTypes.TYPE);

    final Set<String> mods = modifierNames(methodDef);

    final boolean isEqualsMethod =
        mods.contains("public") && !mods.contains("static") &&
        eqName(methodName, "equals") &&
        eqTypeASTName(methodType, "boolean") &&
        eqTypeASTName(paramType, "Object") &&
        eqParamCount(params, 1);

    if (!isEqualsMethod) {
      log(line, column, "instanceof may only be used in the equals method");
    }
  }

  private Set<String> modifierNames(final DetailAST ast) {
    final DetailAST modAst = ast.findFirstToken(TokenTypes.MODIFIERS);
    require(modAst != null, "AST doesn't contain modifiers");

    final Set<String> mods = new HashSet<>();
    for (DetailAST mod = modAst.getFirstChild(); mod != null; mod = mod.getNextSibling()) {
      mods.add(mod.getText());
    }
    return mods;
  }

  private boolean eqParamCount(final DetailAST ast, final int count) {
    require(ast.getType() == TokenTypes.PARAMETERS, "not a parameter list");
    return ast.getChildCount() == count;
  }

  private boolean eqName(final DetailAST ast, final String name) {
    return ast.getText().equals(name);
  }

  private boolean eqTypeASTName(final DetailAST ast, final String name) {
    require(ast.getType() == TokenTypes.TYPE, "not a type");
    return ast.getFirstChild().getText().equals(name);
  }

  private void require(final boolean requirement, final String message) {
    if (!requirement) {
      throw new AssertionError(message);
    }
  }
}
