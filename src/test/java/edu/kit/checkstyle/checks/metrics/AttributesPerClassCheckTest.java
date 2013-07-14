package edu.kit.checkstyle.checks.metrics;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import static edu.kit.checkstyle.CollectionUtils.*;
import static edu.kit.checkstyle.checks.metrics.AttributesPerClassCheck.*;


public class AttributesPerClassCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(AttributesPerClassCheck.class);

  @Test
  public void foundAttribute() {
    test(config, mkList(
        metricAt(4, 1, METRIC + ":3"),
        metricAt(16, 3, METRIC + ":2")));
  }

}
