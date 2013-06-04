package edu.kit.checkstyle.checks.instanceofusagechecktest;

public class instanceofExists {
  public final boolean equals(Object i) {
    if (i instanceof String) {}
    return false;
  }
}