package edu.kit.checkstyle.checks;

import java.util.Arrays;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class StaticUsageCheckTest extends BaseCheckTestSupport {

  final DefaultConfiguration config = createCheckConfig(StaticUsageCheck.class);

  @Test
  public void noStaticExists() throws Exception {
    test(config, "noStaticExists", NO_ERR);
  }

  @Test
  public void publicStaticFinalValue() throws Exception {
    test(config, "publicStaticFinalValue", NO_ERR);
  }

  @Test
  public void staticFound() throws Exception {
    test(config, "staticFound", Arrays.asList(errAt(7, 3)));
  }

  @Test
  public void staticImportIsNoError() throws Exception {
    test(config, "staticImportIsNoError", NO_ERR);
  }

  @Test
  public void singletonIsNoError() throws Exception {
    test(config, "singletonIsNoError", NO_ERR);
  }

  @Test
  public void helperFunctionIsNoError() throws Exception {
    test(config, "helperFunctionIsNoError", NO_ERR);
  }
}
