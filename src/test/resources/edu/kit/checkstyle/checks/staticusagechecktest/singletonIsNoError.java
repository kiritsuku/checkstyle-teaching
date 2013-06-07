package edu.kit.checkstyle.checks.staticusagechecktest;


public class singletonIsNoError {
  private static final singletonIsNoError instance = new singletonIsNoError();
  private singletonIsNoError() {}
  
  static singletonIsNoError getInstance() {
    return instance;
  }
}