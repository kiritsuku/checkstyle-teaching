package edu.kit.checkstyle.checks.metrics;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import static edu.kit.checkstyle.CollectionUtils.*;
import static edu.kit.checkstyle.checks.metrics.MaxNestedBlockDepthCheck.*;

public class MaxNestedBlockDepthCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(MaxNestedBlockDepthCheck.class);

  @Test
  public void depthTest() {
    test(config, mkList(
        metricAt(6, 3, METRIC + ":0"),
        metricAt(12, 3, METRIC + ":3"),
        metricAt(38, 3, METRIC + ":5"),
        metricAt(63, 3, METRIC + ":4"),
        metricAt(80, 3, METRIC + ":1")));
  }

}
