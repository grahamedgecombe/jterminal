/*
 * Copyright (c) 2009-2011 Graham Edgecombe.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.grahamedgecombe.jterminal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JComponent;
import javax.swing.JScrollBar;

import com.grahamedgecombe.jterminal.vt100.Vt100TerminalModel;

/**
 * A Swing terminal emulation component.
 * @author Graham Edgecombe
 */
public class JTerminal extends JComponent {

	/**
	 * The component that actually draws the terminal.
	 * @author Graham Edgecombe
	 */
	private class Terminal extends JComponent {

		/**
		 * The cell width in pixels.
		 */
		private static final int CELL_WIDTH = 8;

		/**
		 * The cell height in pixels.
		 */
		private static final int CELL_HEIGHT = 12;

		/**
		 * The font.
		 */
		private final Font font = new Font("Monospaced", Font.PLAIN, CELL_HEIGHT);

		/**
		 * The unique serial version id.
		 */
		private static final long serialVersionUID = 8832602559840777008L;

		/**
		 * Creates the terminal.
		 */
		private Terminal() {
			setDoubleBuffered(true);
		}

		@Override
		public Dimension getMinimumSize() {
			return new Dimension(model.getColumns() * CELL_WIDTH, model.getRows() * CELL_HEIGHT);
		}

		@Override
		public Dimension getMaximumSize() {
			return getMinimumSize();
		}

		@Override
		public Dimension getPreferredSize() {
			return getMinimumSize();
		}

		@Override
		public void paint(Graphics g) {
			g.setFont(font);

			int width = model.getColumns();
			int height = model.getBufferSize();

			g.setColor(model.getDefaultBackgroundColor());
			g.fillRect(0, 0, width * CELL_WIDTH, height * CELL_HEIGHT);

			int start = scrollBar == null ? 0 : scrollBar.getValue();
			for (int y = start; y < height; y++) {
				for (int x = 0; x < width; x++) {
					TerminalCell cell = model.getCell(x, y);
					boolean cursorHere = model.getCursorRow() == y && model.getCursorColumn() == x;

					if (cursorHere && cell == null) {
						cell = new TerminalCell(' ', model.getDefaultBackgroundColor(), model.getDefaultForegroundColor());
					}

					if (cell != null) {
						int px = x * CELL_WIDTH;
						int py = (y - start) * CELL_HEIGHT;

						g.setColor(cursorHere ? cell.getForegroundColor() : cell.getBackgroundColor());
						g.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);

						g.setColor(cursorHere ? cell.getBackgroundColor() : cell.getForegroundColor());
						g.drawChars(new char[] { cell.getCharacter() }, 0, 1, px, py + CELL_HEIGHT);
					}
				}
			}
		}

	}

	/**
	 * The unique serial version id.
	 */
	private static final long serialVersionUID = 2871625194146986567L;

	/**
	 * The scroll bar.
	 */
	private JScrollBar scrollBar;

	/**
	 * The current model.
	 */
	private TerminalModel model;

	/**
	 * Creates a terminal with the a new {@link Vt100TerminalModel}.
	 */
	public JTerminal() {
		this(new Vt100TerminalModel());
	}

	/**
	 * Creates a terminal with the specified model.
	 * @param model The model.
	 */
	public JTerminal(TerminalModel model) {
		setModel(model);
		init();
	}

	/**
	 * Initializes the terminal.
	 */
	private void init() {
		setLayout(new BorderLayout(0, 0));

		int rows = model.getRows();
		int bufferSize = model.getBufferSize();

		if (bufferSize > rows) {
			scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, rows, 0, bufferSize + 1);
			scrollBar.addAdjustmentListener(new AdjustmentListener() {
				@Override
				public void adjustmentValueChanged(AdjustmentEvent evt) {
					repaint();
				}
			});
			add(BorderLayout.LINE_END, scrollBar);
		}

		add(BorderLayout.CENTER, new Terminal());

		repaint();
	}

	/**
	 * Gets the current terminal model.
	 * @return The current terminal model.
	 */
	public TerminalModel getModel() {
		return model;
	}

	/**
	 * Sets the current terminal model.
	 * @param model The new terminal model.
	 * @throws NullPointerException if the model is {@code null}.
	 */
	public void setModel(TerminalModel model) {
		if (model == null) {
			throw new NullPointerException("model");
		}
		this.model = model;
	}

	/**
	 * Prints a line to the terminal. This method is shorthand for:
	 * {@code getModel().print(str.concat("\r\n"));}
	 * @param str The string to print.
	 * @throws NullPointerException if the string is null.
	 */
	public void println(String str) {
		if (str == null) {
			throw new NullPointerException("str");
		}
		print(str.concat("\r\n"));
	}

	/**
	 * Prints a string to the terminal. This method is shorthand for:
	 * {@code getModel().print(str);}
	 * @param str The string to print.
	 * @throws NullPointerException if the string is null.
	 */
	public void print(String str) {
		model.print(str);
	}

}

