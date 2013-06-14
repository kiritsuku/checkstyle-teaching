package edu.kit.checkstyle.checks.callhierarchycomplexitychecktest;


public class methodChainIsDuplicate {

  void test() {
    int v1 = getA().getB().getC().getValue1();
    int v2 = getA().getB().getC().getValue2(); // duplicate
  }
  
  A getA() {
    return new A();
  }
}

class A {
  B getB() {
    return new B();
  }
}

class B {
  C getC() {
    return new C();
  }
}

class C {
  int getValue1() {
    return 0;
  }
  int getValue2() {
    return 0;
  }
}
