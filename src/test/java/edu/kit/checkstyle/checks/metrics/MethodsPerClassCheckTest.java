package edu.kit.checkstyle.checks.metrics;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import static edu.kit.checkstyle.CollectionUtils.*;
import static edu.kit.checkstyle.checks.metrics.MethodsPerClassCheck.*;


public class MethodsPerClassCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(MethodsPerClassCheck.class);

  @Test
  public void foundMethodInClass() {
    test(config, mkList(
        metricAt(4, 1, METRIC + ":2")));
  }

  @Test
  public void foundMethodInInterface() {
    test(config, mkList(
        metricAt(4, 1, METRIC + ":2")));
  }

}
