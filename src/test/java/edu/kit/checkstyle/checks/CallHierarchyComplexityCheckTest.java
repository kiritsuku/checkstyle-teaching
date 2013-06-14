package edu.kit.checkstyle.checks;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class CallHierarchyComplexityCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(CallHierarchyComplexityCheck.class);

  @Test
  public void singleMethodCallIsNoDuplicate() {
    test(config, NO_ERR);
  }

  @Test
  public void methodChainIsDuplicate() {
    test(config, Arrays.asList(errAt(8, 14)));
  }

  @Test @Ignore
  public void subChainIsDuplicate() {
    test(config, Arrays.asList(errAt(8, 14), errAt(9, 14)));
  }
}