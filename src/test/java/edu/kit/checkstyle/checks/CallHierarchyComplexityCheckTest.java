package edu.kit.checkstyle.checks;

import org.junit.Ignore;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import static edu.kit.checkstyle.CollectionUtils.*;


public class CallHierarchyComplexityCheckTest extends BaseCheckTestSupport {

  private final DefaultConfiguration config = createCheckConfig(CallHierarchyComplexityCheck.class);

  @Test @Ignore
  public void singleMethodCallIsDuplicate() {
    test(config, mkList(errAt(8, 14)));
  }

  @Test @Ignore
  public void methodChainIsDuplicate() {
    test(config, mkList(errAt(8, 14)));
  }

  @Test
  public void subChainIsDuplicate() {
    test(config, mkList(errAt(8, 14), errAt(9, 14)));
  }
}