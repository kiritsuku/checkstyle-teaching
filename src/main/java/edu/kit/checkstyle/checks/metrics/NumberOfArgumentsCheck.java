package edu.kit.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


/**
 * Counts the number of arguments of methods and constructors.
 *
 * @since JDK1.7, Jul 19, 2013
 */
public class NumberOfArgumentsCheck extends MetricCheck {

  public static final String METRIC = "number-of-arguments";

  @Override
  public int[] getDefaultTokens() {
    return new int[] { TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF };
  }

  @Override
  protected String metric() {
    return METRIC;
  }

  @Override
  protected void execute(final DetailAST ast) {
    final int count = parameterCount(ast.findFirstToken(TokenTypes.PARAMETERS));
    logMetric(ast, count);
  }

  private int parameterCount(final DetailAST ast) {
    int count = 0;
    for (DetailAST cur = ast.getFirstChild(); cur != null; cur = cur.getNextSibling()) {
      if (cur.getType() == TokenTypes.PARAMETER_DEF) {
        count += 1;
      }
    }
    return count;
  }
}
