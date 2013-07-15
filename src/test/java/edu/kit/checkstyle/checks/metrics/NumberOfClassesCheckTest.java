package edu.kit.checkstyle.checks.metrics;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import static edu.kit.checkstyle.CollectionUtils.*;
import static edu.kit.checkstyle.checks.metrics.NumberOfClassesCheck.*;


public class NumberOfClassesCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(NumberOfClassesCheck.class);

  @Test
  public void foundClassesAndInterfaces() {
    test(config, mkList(
        metricAt(4, 1, METRIC + ":1"),
        metricAt(6, 3, METRIC + ":1"),
        metricAt(9, 1, METRIC + ":1")));
  }

}
