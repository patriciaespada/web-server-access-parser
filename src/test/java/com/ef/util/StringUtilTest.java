package com.ef.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testUnquote() {
		assertEquals(null, StringUtil.unquote(null));
		assertEquals("", StringUtil.unquote(""));
		assertEquals("ABC", StringUtil.unquote("ABC"));
		assertEquals("ABC", StringUtil.unquote("\"ABC\""));
		assertEquals("A\"BC", StringUtil.unquote("\"A\"BC\""));
		assertEquals("\"", StringUtil.unquote("\"\"\""));
	}
	
}
