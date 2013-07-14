package edu.kit.checkstyle.checks.metrics.attributesperclasschecktest;


public class foundAttribute {

  private int a = 0;
  
  int b = 0;
  
  void f() {
    int j = 0; // should not be detected
  }
  
  int c = 0;
  
  class inner { // separate class
    int d = 0;
    static int e = 0;
  }
}
