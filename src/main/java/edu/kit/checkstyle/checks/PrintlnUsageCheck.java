package edu.kit.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import edu.kit.checkstyle.TokenSearcherCheck;


/**
 * Checks if the 'println' method is called.
 *
 * @since JDK1.7, 07.06.2013
 */
public class PrintlnUsageCheck extends TokenSearcherCheck {

  private static final String msg = "The use of 'println' is discouraged";

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

    if (ast.getText().equals("println")) {
      log(line, column, msg);
    }
  }
}
