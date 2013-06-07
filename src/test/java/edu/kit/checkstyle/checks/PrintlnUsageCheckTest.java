package edu.kit.checkstyle.checks;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class PrintlnUsageCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(PrintlnUsageCheck.class);

  @Test
  public void noPrintlnExists() throws Exception {
    verify(config, fileNameWithSuffix("noPrintlnExists"), ArrayUtils.EMPTY_STRING_ARRAY);
  }

  @Test
  public void printlnFound() throws Exception {
    checkTest(config, fileWithSuffix("printlnFound"),
        Arrays.asList(errAt(7, 16)));
  }
}
