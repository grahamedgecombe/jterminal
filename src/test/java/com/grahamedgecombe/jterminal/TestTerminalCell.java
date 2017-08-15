package com.grahamedgecombe.jterminal;

import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Color;

public class TestTerminalCell {

  @Test(timeout = 4000)
  public void testTerminalCell()  throws Throwable  {
      Color color0 = new Color(5134, true);
      TerminalCell terminalCell0 = new TerminalCell('i', color0, color0);
      terminalCell0.getForegroundColor();
      assertEquals('i', terminalCell0.getCharacter());
  }

}
