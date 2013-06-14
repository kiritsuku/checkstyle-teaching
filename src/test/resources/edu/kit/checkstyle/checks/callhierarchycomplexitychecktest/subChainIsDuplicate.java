package edu.kit.checkstyle.checks.callhierarchycomplexitychecktest;


public class subChainIsDuplicate {

  void test() {
    A a = getA();
    int v1 = getA().getB().getC().getValue1(); // duplicate
    int v2 = a.getB().getC().getValue2(); // duplicate
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
