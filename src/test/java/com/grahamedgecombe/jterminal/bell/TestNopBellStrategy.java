package com.grahamedgecombe.jterminal.bell;

import org.junit.Test;

public class TestNopBellStrategy {

  @Test(timeout = 4000)
  public void testShouldNotThrowAnything()  throws Throwable  {
      NopBellStrategy nopBellStrategy0 = new NopBellStrategy();
      nopBellStrategy0.soundBell();
  }
}
