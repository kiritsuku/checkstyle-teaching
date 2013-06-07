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

  protected boolean eqName(final DetailAST ast, final String name) {
    return ast.getText().equals(name);
  }

  /**
   * Searches for the method a token is placed into.
   *
   * @param ast
   *        The AST to start the search
   * @return The containing method or {@code null} if none exists.
   */
  protected DetailAST getContainingMethod(final DetailAST ast) {
    if (ast.getType() == TokenTypes.METHOD_DEF) {
      return ast;
    }

    DetailAST methodDef = ast.getParent();
    while (methodDef != null && methodDef.getType() != TokenTypes.METHOD_DEF) {
      methodDef = methodDef.getParent();
    }
    return methodDef;
  }

}
