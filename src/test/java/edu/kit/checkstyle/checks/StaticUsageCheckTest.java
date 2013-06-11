package edu.kit.checkstyle.checks;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class StaticUsageCheckTest extends BaseCheckTestSupport {

  final DefaultConfiguration config = createCheckConfig(StaticUsageCheck.class);

  @Test
  public void noStaticExists() throws Exception {
    verify(config, fileNameWithSuffix("noStaticExists"), ArrayUtils.EMPTY_STRING_ARRAY);
  }

  @Test
  public void publicStaticFinalValue() throws Exception {
    verify(config, fileNameWithSuffix("publicStaticFinalValue"), ArrayUtils.EMPTY_STRING_ARRAY);
  }

  @Test
  public void staticFound() throws Exception {
    checkTest(config, fileWithSuffix("staticFound"),
        Arrays.asList(errAt(7, 3), errAt(10, 3)));
  }

  @Test
  public void staticImportIsNoError() throws Exception {
    verify(config, fileNameWithSuffix("staticImportIsNoError"), ArrayUtils.EMPTY_STRING_ARRAY);
  }
}
