package com.grahamedgecombe.jterminal.bell;

import org.junit.Test;

public class TestBeepBellStrategy {

  @Test(timeout = 4000)
  public void testShouldNotThrowAnything() throws Throwable  {
      BeepBellStrategy beepBellStrategy0 = new BeepBellStrategy();
      beepBellStrategy0.soundBell();
  }
}
