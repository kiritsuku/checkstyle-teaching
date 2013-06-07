package edu.kit.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import edu.kit.checkstyle.TokenSearcherCheck;


/**
 * Checks if the 'System.exit' method is called outside of the main method.
 *
 * @since JDK1.7, 07.06.2013
 */
public class ExitUsageCheck extends TokenSearcherCheck {

  private static final String msg = "The usage of 'System.exit' outside of the main method is discouraged";

  @Override
  public int[] getDefaultTokens() {
    return new int[] {
        TokenTypes.IDENT
    };
  }

  @Override
  public void visitToken(final DetailAST ast) {
    final int line = ast.getLineNo();
    final int column = ast.getColumnNo();

    final boolean isInMainMethod =
        eqName(ast, "exit") &&
        eqName(ast.getPreviousSibling(), "System") &&
        eqName(getContainingMethodName(ast), "main");

    if (!isInMainMethod) {
      log(line, column, msg);
    }
  }

  private DetailAST getContainingMethodName(final DetailAST ast) {
    require(ast.getType() == TokenTypes.METHOD_DEF, "AST is not a method definition");
    return getContainingMethod(ast).findFirstToken(TokenTypes.IDENT);
  }
}
