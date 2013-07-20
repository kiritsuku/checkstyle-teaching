package edu.kit.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


/**
 * Returns a 1 for every occurrence of a method. The number of ones needs to be
 * caught afterwards by an {@link AuditListener}.
 *
 * @since JDK1.7, Jul 20, 2013
 */
public class NumberOfMethodsPerClassCheck extends MetricCheck {

  public static final String METRIC = "number-of-methods-per-class";

  @Override
  protected String metric() {
    return METRIC;
  }

  @Override
  public int[] getDefaultTokens() {
    return new int[] { TokenTypes.METHOD_DEF };
  }

  @Override
  protected void execute(final DetailAST ast) {
    logMetric(ast, 1);
  }

}
