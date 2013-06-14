package edu.kit.checkstyle.checks.callhierarchycomplexitychecktest;


public class singleMethodCallIsNoDuplicate {

  void test() {
    int v1 = getValue();
    int v2 = getValue(); // no duplicate
  }
  
  int getValue() {
    return 0;
  }
}
