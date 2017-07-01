package com.grahamedgecombe.jterminal;

import org.junit.Test;
import static org.junit.Assert.*;
import com.grahamedgecombe.jterminal.vt100.Vt100TerminalModel;
import java.awt.Dimension;

public class TestJTerminal {

  @Test(timeout = 4000)
  public void testJTerminalInitialization()  throws Throwable  {
      Vt100TerminalModel vt100TerminalModel0 = new Vt100TerminalModel(0, 0, 0);
      JTerminal jTerminal0 = new JTerminal(vt100TerminalModel0);
      TerminalModel terminalModel0 = jTerminal0.getModel();
      assertSame(vt100TerminalModel0, terminalModel0);
  }

  @Test(timeout = 4000)
  public void testGetCursorColumn()  throws Throwable  {
      JTerminal jTerminal0 = new JTerminal();
      jTerminal0.print("com.grahamedgecombe.jterminal.JTerminal");
      TerminalModel terminalModel0 = jTerminal0.getModel();
      assertEquals(39, terminalModel0.getCursorColumn());
  }

  @Test(timeout = 4000)
  public void testGetCursorRow()  throws Throwable  {
      JTerminal jTerminal0 = new JTerminal();
      jTerminal0.println("");
      TerminalModel terminalModel0 = jTerminal0.getModel();
      assertEquals(1, terminalModel0.getCursorRow());
  }

  @Test(timeout = 4000)
  public void testJTerminalDimension()  throws Throwable  {
      JTerminal jTerminal0 = new JTerminal();
      Dimension dimension0 = jTerminal0.getPreferredSize();
      assertEquals(300, dimension0.height);
      assertEquals(640, dimension0.width);
  }

  @Test(timeout = 4000)
  public void testFocusTraversalPolicySet()  throws Throwable  {
      Vt100TerminalModel vt100TerminalModel0 = new Vt100TerminalModel(4517, 521, 4517);
      JTerminal jTerminal0 = new JTerminal(vt100TerminalModel0);
      assertFalse(jTerminal0.isFocusTraversalPolicySet());
  }
}
