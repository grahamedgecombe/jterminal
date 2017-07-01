package com.grahamedgecombe.jterminal;

import org.junit.Test;
import static org.junit.Assert.*;
import com.grahamedgecombe.jterminal.vt100.Vt100TerminalModel;

public class TestAbstractTerminalModel {

  @Test(timeout = 4000)
  public void testShouldNotThrowAnything()  throws Throwable  {
      Vt100TerminalModel vt100TerminalModel0 = new Vt100TerminalModel(4740, 4740);
      vt100TerminalModel0.getCursorRow();
      vt100TerminalModel0.getColumns();
      // Undeclared exception!
      vt100TerminalModel0.clear();
  }


  @Test(timeout = 4000)
  public void testRandomInputTest()  throws Throwable  {
      Vt100TerminalModel vt100TerminalModel0 = new Vt100TerminalModel();
      vt100TerminalModel0.moveCursorDown(0);
      vt100TerminalModel0.print("");
      vt100TerminalModel0.getCell(0, 0);
      vt100TerminalModel0.setCell(0, 0, (TerminalCell) null);
      vt100TerminalModel0.moveCursorForward(2583);
      vt100TerminalModel0.clear();
      vt100TerminalModel0.moveCursorBack(0);
      vt100TerminalModel0.print("");
      assertEquals(79, vt100TerminalModel0.getCursorColumn());
  }

}
