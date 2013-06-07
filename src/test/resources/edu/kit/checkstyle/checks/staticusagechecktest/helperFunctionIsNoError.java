package edu.kit.checkstyle.checks.staticusagechecktest;


public class helperFunctionIsNoError {
  final int value;
  private helperFunctionIsNoError(final int value) {
    this.value = value;
  }
  static helperFunctionIsNoError of(final int value) {
    return new helperFunctionIsNoError(value);
  }
}
