package edu.kit.checkstyle.checks;

import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import edu.kit.checkstyle.TokenSearcherCheck;


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

    final DetailAST block = ast.getParent().getParent();

    if (hasModifiers(block)) {
      final Set<String> mods = modifierNames(block);
      if (!mods.contains("final")) {
        log(line, column, msg);
      }
    } else {
      log(line, column, msg);
    }
  }
}
