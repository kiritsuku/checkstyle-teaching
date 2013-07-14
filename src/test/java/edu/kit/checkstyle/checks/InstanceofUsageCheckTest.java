package edu.kit.checkstyle.checks;

import java.util.Arrays;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class InstanceofUsageCheckTest extends BaseCheckTestSupport {

  final DefaultConfiguration config = createCheckConfig(InstanceofUsageCheck.class);

  @Test
  public void noInstanceofExists() throws Exception {
    test(config, "noInstanceofExists", NO_REPORT);
  }

  @Test
  public void instanceofExists() throws Exception {
    test(config, "instanceofExists", NO_REPORT);
  }

  @Test
  public void instanceofExistsWithError() throws Exception {
    test(config, "instanceofExistsWithError", Arrays.asList(
        errAt(8, 11), errAt(14, 11), errAt(20, 13),
        errAt(26, 11), errAt(32, 11), errAt(38, 11),
        errAt(44, 11)));
  }

  @Test
  public void nestedInstanceofExists() throws Exception {
    test(config, "nestedInstanceofExists", NO_REPORT);
  }
}
