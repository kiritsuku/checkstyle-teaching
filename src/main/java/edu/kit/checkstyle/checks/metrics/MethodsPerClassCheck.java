package edu.kit.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


/**
 * Counts the number of methods in a class or an interface.
 *
 * @since JDK1.7, Jul 15, 2013
 */
public class MethodsPerClassCheck extends MetricCheck {

  public static final String METRIC = "methods-per-class";

  @Override
  protected String metric() {
    return METRIC;
  }

  @Override
  public int[] getDefaultTokens() {
    return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
  }

  @Override
  public void visitToken(final DetailAST ast) {
    final DetailAST body = ast.findFirstToken(TokenTypes.OBJBLOCK);
    final int count = countTokenType(body, TokenTypes.METHOD_DEF);
    logMetric(ast, count);
  }

}
