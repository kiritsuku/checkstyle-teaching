package edu.kit.checkstyle.checks.instanceofusagechecktest;


public class nestedInstanceofExists {
  public boolean equals(Object i) {
    if (i.hashCode() == 0) {
      if (i instanceof String) {}
    }
    return false;
  }
}
