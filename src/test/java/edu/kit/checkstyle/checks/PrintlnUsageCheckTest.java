package edu.kit.checkstyle.checks;

import java.util.Arrays;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class PrintlnUsageCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(PrintlnUsageCheck.class);

  @Test
  public void noPrintlnExists() throws Exception {
    test(config, "noPrintlnExists", NO_ERR);
  }

  @Test
  public void printlnFound() throws Exception {
    test(config, "printlnFound", Arrays.asList(errAt(7, 16)));
  }
}
