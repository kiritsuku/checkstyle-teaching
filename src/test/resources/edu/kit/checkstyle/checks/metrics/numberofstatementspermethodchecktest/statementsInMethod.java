package edu.kit.checkstyle.checks.metrics.numberofstatementspermethodchecktest;


public class statementsInMethod {
  
  void a() {
    int i = 0;
    String j = "";
    g();
  }

  void b() {
    if (true) {
      g();
    } else if (true) {
      g();
    } else if (true) {
      g();
    } else {
      g();
    }
    if (true) {
      g();
    } else {
      if (true) {
        g();
      }
    }
  }
  
  void c() {
    while (true) {
      f();
    }
    for (int i = start(); i < cond(); ++i) {
      f();
    }
    try {
      g();
    } catch (Exeption1 e) {
      g();
    } catch (Exeption2 e) {
      g();
    } finally {
      g();
    }
  }
  
  void d(int key) {
    switch (key) {
      case 0:
      case 1:
        g(); break;
      case 2:
        g(); break;
      default:
        g();
    }
  }
  
  void e() {
    synchronized (this) {
      g();
    }
  }
}
