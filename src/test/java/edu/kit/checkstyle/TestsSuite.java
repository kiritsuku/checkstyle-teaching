package edu.kit.checkstyle;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.kit.checkstyle.checks.CallHierarchyComplexityCheckTest;
import edu.kit.checkstyle.checks.DiscouragedMethodCallCheckTest;
import edu.kit.checkstyle.checks.InstanceofUsageCheckTest;
import edu.kit.checkstyle.checks.StaticUsageCheckTest;
import edu.kit.checkstyle.checks.metrics.AttributesPerClassCheckTest;
import edu.kit.checkstyle.checks.metrics.MaxNestedBlockDepthCheckTest;
import edu.kit.checkstyle.checks.metrics.MethodsPerClassCheckTest;
import edu.kit.checkstyle.checks.metrics.NumberOfClassesCheckTest;
import edu.kit.checkstyle.checks.metrics.NumberOfStatementsPerMethodCheckTest;

@RunWith(Suite.class)
@SuiteClasses({
  CallHierarchyComplexityCheckTest.class,
  DiscouragedMethodCallCheckTest.class,
  InstanceofUsageCheckTest.class,
  StaticUsageCheckTest.class,
  AttributesPerClassCheckTest.class,
  MethodsPerClassCheckTest.class,
  NumberOfClassesCheckTest.class,
  NumberOfStatementsPerMethodCheckTest.class,
  MaxNestedBlockDepthCheckTest.class
})
public class TestsSuite {

}
