package edu.kit.checkstyle.checks.callhierarchycomplexitychecktest;


public class singleMethodCallIsDuplicate {

  void test() {
    int v1 = getValue();
    int v2 = getValue(); // duplicate
  }
  
  int getValue() {
    return 0;
  }
}
