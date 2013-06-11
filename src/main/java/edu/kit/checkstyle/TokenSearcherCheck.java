package edu.kit.checkstyle;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


public abstract class TokenSearcherCheck extends Check {

  protected void require(final boolean requirement, final String message) {
    if (!requirement) {
      throw new AssertionError(message);
    }
  }

  protected Set<String> modifierNames(final DetailAST ast) {
    final DetailAST modAst = ast.findFirstToken(TokenTypes.MODIFIERS);
    require(modAst != null, "AST doesn't contain modifiers");

    final Set<String> mods = new HashSet<>();
    for (DetailAST mod = modAst.getFirstChild(); mod != null; mod = mod.getNextSibling()) {
      mods.add(mod.getText());
    }
    return mods;
  }

  protected boolean hasModifiers(final DetailAST ast) {
    return ast.findFirstToken(TokenTypes.MODIFIERS) != null;
  }

}
