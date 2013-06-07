package edu.kit.checkstyle.checks;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class ExitUsageCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(ExitUsageCheck.class);

  @Test
  public void noErrorInMainMethod() throws Exception {
    verify(config, fileNameWithSuffix("noErrorInMainMethod"), ArrayUtils.EMPTY_STRING_ARRAY);
  }

  @Test
  public void errorOutsideOfMainMethod() throws Exception {
    checkTest(config, fileWithSuffix("errorOutsideOfMainMethod"),
        Arrays.asList(errAt(7, 12)));
  }
}
