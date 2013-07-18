package edu.kit.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


/**
 * Returns a 1 for every occurrence of a class, an interface or an enum. The
 * number of ones needs to be caught afterwards by an {@link AuditListener}.
 *
 * @since JDK1.7, Jul 15, 2013
 */
public class NumberOfClassesCheck extends MetricCheck {

  public static final String METRIC = "number-of-classes";

  @Override
  public int[] getDefaultTokens() {
    return new int[] {
        TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.ENUM
    };
  }

  @Override
  protected String metric() {
    return METRIC;
  };

  @Override
  protected void execute(final DetailAST ast) {
    logMetric(ast, 1);
  }

}
