package edu.kit.checkstyle.checks.metrics;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import static edu.kit.checkstyle.CollectionUtils.*;
import static edu.kit.checkstyle.checks.metrics.NumberOfArgumentsCheck.*;


public class NumberOfArgumentsCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(NumberOfArgumentsCheck.class);

  @Test
  public void checkArguments() {
    test(config, mkList(
        metricAt(6, 3, METRIC + ":0"),
        metricAt(8, 3, METRIC + ":1"),
        metricAt(10, 3, METRIC + ":4")));
  }

}
