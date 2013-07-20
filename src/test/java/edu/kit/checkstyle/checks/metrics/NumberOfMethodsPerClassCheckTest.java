package edu.kit.checkstyle.checks.metrics;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import static edu.kit.checkstyle.CollectionUtils.*;
import static edu.kit.checkstyle.checks.metrics.NumberOfMethodsPerClassCheck.*;


public class NumberOfMethodsPerClassCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(NumberOfMethodsPerClassCheck.class);

  @Test
  public void checkMethods() {
    test(config, mkList(
        metricAt(6, 3, METRIC + ":1"),
        metricAt(8, 3, METRIC + ":1"),
        metricAt(10, 3, METRIC + ":1")));
  }

}
