package edu.kit.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;


/**
 * Base class for checks that report metrics. All metric messages sent by this
 * class are reported as {@link SeverityLevel#INFO}.
 *
 * @since JDK1.7, Jul 14, 2013
 */
public abstract class MetricCheck extends Check {

  @Override
  public final void init() {
    setSeverity(SeverityLevel.INFO.getName());
  }

  /**
   * Logs a metric message.
   *
   * @param ast
   *        The AST the metric should be reported at
   * @param value
   *        The value calculated by the metric
   */
  protected final void logMetric(final DetailAST ast, final Object value) {
    log(ast, "metric:" + metric() + ":" + value);
  }

  /**
   * The message that classifies the metric.
   */
  protected abstract String metric();

}
