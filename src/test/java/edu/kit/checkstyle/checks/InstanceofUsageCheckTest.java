package edu.kit.checkstyle.checks;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class InstanceofUsageCheckTest extends BaseCheckTestSupport {

  final DefaultConfiguration config = createCheckConfig(InstanceofUsageCheck.class);

  @Test
  public void noInstanceofExists() throws Exception { // comment
    verify(config, fileNameWithSuffix("noInstanceofExists"), ArrayUtils.EMPTY_STRING_ARRAY);
  }

  @Test
  public void instanceofExists() throws Exception {
    verify(config, fileNameWithSuffix("instanceofExists"), ArrayUtils.EMPTY_STRING_ARRAY);
  }

  @Test
  public void instanceofExistsWithError() throws Exception {
    checkTest(config, fileWithSuffix("instanceofExistsWithError"),
        Arrays.asList(errAt(8, 11), errAt(14, 11), errAt(20, 13), errAt(26, 11), errAt(32, 11), errAt(38, 11)));
  }

  @Test
  public void nestedInstanceofExists() throws Exception {
    verify(config, fileNameWithSuffix("nestedInstanceofExists"), ArrayUtils.EMPTY_STRING_ARRAY);
  }
}
