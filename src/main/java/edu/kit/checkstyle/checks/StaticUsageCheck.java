package edu.kit.checkstyle.checks;

import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import edu.kit.checkstyle.TokenSearcherCheck;


/**
 * Checks the existence of 'static' keywords at variables. For variables
 * 'static' is only allowed together with the 'final' keyword, because this
 * marks it as a constant.
 *
 * For methods no errors are reported if they are marked as 'static'.
 *
 * @since JDK1.7, Jun 4, 2013
 */
public class StaticUsageCheck extends TokenSearcherCheck {

  private static final String msg = "'static' may only be used on variables together with 'final'";

  @Override
  public int[] getDefaultTokens() {
    return new int[] {
        TokenTypes.LITERAL_STATIC
    };
  }

  @Override
  public void visitToken(final DetailAST ast) {
    final int line = ast.getLineNo();
    final int column = ast.getColumnNo();

    if (ast.getParent().getType() == TokenTypes.STATIC_IMPORT) {
      return;
    }

    final DetailAST block = ast.getParent().getParent();

    if (block.getType() == TokenTypes.VARIABLE_DEF) {
      final Set<String> mods = modifierNames(block);
      if (!mods.contains("final")) {
        log(line, column, msg);
      }
    }
  }
}
