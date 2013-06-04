package edu.kit.checkstyle.checks.instanceofusagechecktest;


public class instanceofExistsWithError {
  
  // wrong signature
  boolean equals(Object i) {
    if (i instanceof String) {}
    return false;
  }
  
  // wrong signature  
  public boolean equal(Object i) {
    if (i instanceof String) {}
    return false;
  }
  
  // wrong signature
  public boolean equals(Object obj, Object j) {
    if (obj instanceof Integer) {}
    return false;
  }

  // wrong signature
  public boolean equals(String i) {
    if (i instanceof String) {}
    return false;
  }
  
  // wrong signature
  public static boolean equals(String i) {
    if (i instanceof String) {}
    return false;
  }
  
  // wrong signature
  public void equals(Object i) {
    if (i instanceof String) {}
  }
  
  // instanceof outside of method
  static Object o;
  static {
    if (o instanceof String) {}
  }
}
