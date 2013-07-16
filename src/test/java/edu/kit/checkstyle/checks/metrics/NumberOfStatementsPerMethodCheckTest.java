package edu.kit.checkstyle.checks.metrics;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import static edu.kit.checkstyle.CollectionUtils.*;
import static edu.kit.checkstyle.checks.metrics.NumberOfStatementsPerMethodCheck.*;

public class NumberOfStatementsPerMethodCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(NumberOfStatementsPerMethodCheck.class);

  @Test
  public void statementsInMethod() {
    test(config, mkList(
        metricAt(6, 3, METRIC + ":3"),
        metricAt(12, 3, METRIC + ":6"),
        metricAt(31, 3, METRIC + ":6"),
        metricAt(49, 3, METRIC + ":3"),
        metricAt(61, 3, METRIC + ":1")));
  }

}
