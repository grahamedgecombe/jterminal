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

package com.grahamedgecombe.jterminal.vt100;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * A test for the {@link AnsiControlSequenceParser} class.
 * @author Graham Edgecombe
 */
public class TestAnsiControlSequenceParser implements AnsiControlSequenceListener {

	/**
	 * The current parser.
	 */
	private AnsiControlSequenceParser parser;

	/**
	 * The list of objects returned through the
	 * {@link AnsiControlSequenceListener} interface.
	 */
	private List<Object> objects = new ArrayList<Object>();

	/**
	 * Sets up the parser and object list.
	 */
	@Before
	public void setUp() {
		objects.clear();
		parser = new AnsiControlSequenceParser(this);
	}

	/**
	 * Tests a broken sequence with the single byte CSI.
	 */
	@Test
	public void testBrokenSingleSequence() {
		parser.parse(new String(new char[] { 155 }));
		parser.parse(new String(new char[] { 'u' }));

		assertEquals(1, objects.size());

		Object obj = objects.get(0);
		assertEquals(AnsiControlSequence.class, obj.getClass());

		AnsiControlSequence seq = (AnsiControlSequence) obj;
		String[] params = seq.getParameters();

		assertEquals('u', seq.getCommand());
		assertEquals(0, params.length);
	}

	/**
	 * Tests a broken sequence with the double byte CSI.
	 */
	@Test
	public void testBrokenDoubleSequence() {
		char[] ch1 = { 27 };
		char[] ch2 = { '[' };
		char[] ch3 = { '3', '0', ';' };
		char[] ch4 = { '4', '0', 'm' };

		parser.parse(new String(ch1));
		parser.parse(new String(ch2));
		parser.parse(new String(ch3));
		parser.parse(new String(ch4));

		assertEquals(1, objects.size());
		Object obj = objects.get(0);
		assertEquals(AnsiControlSequence.class, obj.getClass());

		AnsiControlSequence seq = (AnsiControlSequence) obj;
		String[] params = seq.getParameters();

		assertEquals('m', seq.getCommand());
		assertEquals(2, params.length);
		assertEquals("30", params[0]);
		assertEquals("40", params[1]);
	}

	/**
	 * Tests an empty string.
	 */
	@Test
	public void testEmpty() {
		parser.parse("");
		assertEquals(0, objects.size());
	}

	/**
	 * Tests a sequence embedded within some text.
	 */
	@Test
	public void testTextAndSequence() {
		char[] ch = { 'h', 'i', 155, 'u', 'b', 'y', 'e' };
		parser.parse(new String(ch));

		assertEquals(3, objects.size());

		Object o1 = objects.get(0);
		Object o2 = objects.get(1);
		Object o3 = objects.get(2);

		assertEquals(String.class, o1.getClass());
		assertEquals(AnsiControlSequence.class, o2.getClass());
		assertEquals(String.class, o3.getClass());

		assertEquals("hi", o1);
		assertEquals("bye", o3);

		AnsiControlSequence seq = (AnsiControlSequence) o2;
		String[] params = seq.getParameters();

		assertEquals(0, params.length);
		assertEquals('u', seq.getCommand());
	}

	/**
	 * Tests parameters within a sequence.
	 */
	@Test
	public void testParameters() {
		char[] ch = { 155, '3', '0', ';', '4', '0', 'm' };
		parser.parse(new String(ch));

		assertEquals(1, objects.size());
		Object obj = objects.get(0);
		assertEquals(AnsiControlSequence.class, obj.getClass());

		AnsiControlSequence seq = (AnsiControlSequence) obj;
		String[] params = seq.getParameters();

		assertEquals('m', seq.getCommand());
		assertEquals(2, params.length);
		assertEquals("30", params[0]);
		assertEquals("40", params[1]);
	}

	/**
	 * Tests with plain text.
	 */
	@Test
	public void testText() {
		parser.parse("Hello, World!");

		assertEquals(1, objects.size());
		Object obj = objects.get(0);
		assertEquals(String.class, obj.getClass());
		assertEquals(obj, "Hello, World!");
	}

	/**
	 * Tests with a single byte CSI.
	 */
	@Test
	public void testSingleCsi() {
		char[] ch = { 155, '6', 'n' };
		parser.parse(new String(ch));

		assertEquals(1, objects.size());
		Object obj = objects.get(0);
		assertEquals(AnsiControlSequence.class, obj.getClass());

		AnsiControlSequence seq = (AnsiControlSequence) obj;
		String[] params = seq.getParameters();
		assertEquals('n', seq.getCommand());
		assertEquals(1, params.length);
		assertEquals("6", params[0]);
	}

	/**
	 * Tests with a double byte CSI.
	 */
	@Test
	public void testDoubleCsi() {
		char[] ch = { 27, '[', 's' };
		parser.parse(new String(ch));

		assertEquals(1, objects.size());
		Object obj = objects.get(0);
		assertEquals(AnsiControlSequence.class, obj.getClass());

		AnsiControlSequence seq = (AnsiControlSequence) obj;
		String[] params = seq.getParameters();
		assertEquals('s', seq.getCommand());
		assertEquals(0, params.length);
	}

	@Override
	public void parsedControlSequence(AnsiControlSequence seq) {
		objects.add(seq);
	}

	@Override
	public void parsedString(String str) {
		objects.add(str);
	}

	/**
	 * EvoSuite-Generated Test
	 * @throws Throwable
	 */
	@Test(timeout = 4000)
	public void test0()  throws Throwable  {
		String[] stringArray0 = new String[1];
		stringArray0[0] = "fxV<|9*0{9P<";
		AnsiControlSequence ansiControlSequence0 = new AnsiControlSequence('\"', stringArray0);
		char char0 = ansiControlSequence0.getCommand();
		assertEquals('\"', char0);
	}

	/**
	 * EvoSuite-Generated Test
	 * @throws Throwable
	 */
	@Test(timeout = 4000)
	public void test1()  throws Throwable  {
		String[] stringArray0 = new String[0];
		AnsiControlSequence ansiControlSequence0 = new AnsiControlSequence('0', stringArray0);
		char char0 = ansiControlSequence0.getCommand();
		assertEquals('0', char0);
	}

	/**
	 * EvoSuite-Generated Test
	 * @throws Throwable
	 */
	@Test(timeout = 4000)
	public void test2()  throws Throwable  {
		String[] stringArray0 = new String[1];
		stringArray0[0] = "";
		AnsiControlSequence ansiControlSequence0 = new AnsiControlSequence('w', stringArray0);
		assertEquals('w', ansiControlSequence0.getCommand());
	}

	/**
	 * EvoSuite-Generated Test
	 * @throws Throwable
	 */
	@Test(timeout = 4000)
	public void test3()  throws Throwable  {
		String[] stringArray0 = new String[1];
		stringArray0[0] = "y_n$";
		AnsiControlSequence ansiControlSequence0 = new AnsiControlSequence('y', stringArray0);
		ansiControlSequence0.getParameters();
		assertEquals('y', ansiControlSequence0.getCommand());
	}

	/**
	 * EvoSuite-Generated Test
	 * @throws Throwable
	 */
	@Test(timeout = 4000)
	public void test4()  throws Throwable  {
		String[] stringArray0 = new String[1];
		AnsiControlSequence ansiControlSequence0 = null;
		try {
			ansiControlSequence0 = new AnsiControlSequence('4', stringArray0);
			fail("Expecting exception: NullPointerException");

		} catch(NullPointerException ignored) {
		}
	}

	/**
	 * EvoSuite-Generated Test
	 * @throws Throwable
	 */
	@Test(timeout = 4000)
	public void test6()  throws Throwable  {
		String[] stringArray0 = new String[0];
		AnsiControlSequence ansiControlSequence0 = new AnsiControlSequence('B', stringArray0);
		ansiControlSequence0.getParameters();
		assertEquals('B', ansiControlSequence0.getCommand());
	}

	/**
	 * EvoSuite-Generated Test
	 * @throws Throwable
	 */
	@Test(timeout = 4000)
	public void test7()  throws Throwable  {
		String[] stringArray0 = new String[0];
		AnsiControlSequence ansiControlSequence0 = new AnsiControlSequence('B', stringArray0);
		char char0 = ansiControlSequence0.getCommand();
		assertEquals('B', char0);
	}

    /**
     * EvoSuite-Generated Test
     * @throws Throwable
     */
    @Test(timeout = 4000)
    public void test8()  throws Throwable  {
        TestAnsiControlSequenceParser testAnsiControlSequenceParser0 = new TestAnsiControlSequenceParser();
        AnsiControlSequenceParser ansiControlSequenceParser0 = new AnsiControlSequenceParser(testAnsiControlSequenceParser0);
        // Undeclared exception!
        try {
            ansiControlSequenceParser0.parse((String) null);
            fail("Expecting exception: NullPointerException");

        } catch(NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
        }
    }


}

