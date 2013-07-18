package edu.kit.checkstyle.checks.metrics.maxnestedblockdepthchecktest;


public class depthTest {
  
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
      if (true) {
        g();
      } else {
        if (true) {
          g();
        }
      }
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
      for (int i = start(); i < cond(); ++i) {
        try {
          try {
            if (true) {
              g();
            }
          } catch (Exception0 e) {}
          g();
        } catch (Exeption1 e) {
          try {} catch (Exception0 e) {}
          g();
        } catch (Exeption2 e) {
          if (true) {
            g();
          }
        } finally {
          try {} catch (Exception0 e) {}
          g();
        }
      }
    }
  }
  
  void d(int key) {
    switch (key) {
      case 0:
      case 1:
        {
          if (true) {
            g();
          }
          break;
        }
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
