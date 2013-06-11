package edu.kit.checkstyle.checks;

import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import edu.kit.checkstyle.TokenSearcherCheck;


/**
 * Checks the existence of 'static' keywords in a file.
 *
 * They are only allowed together with a 'final' keyword.
 *
 * TODO:
 * - check singletons?
 * - check helper functions?
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

/*
// singleton
class X {
  private static final X x = new X();
  private X() {}

  static X getInstance() {
    return x;
  }
}

// helper function
class IntHolder {
  final int value;
  private IntHolder(final int value) {
    this.value = value;
  }
  static IntHolder of(final int value) {
    return new IntHolder(value);
  }
}
*/