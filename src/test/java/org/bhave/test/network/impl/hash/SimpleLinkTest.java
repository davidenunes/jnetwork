package org.bhave.test.network.impl.hash;

import static org.junit.Assert.*;

import java.util.Properties;

import org.bhave.network.impl.hash.SimpleLink;
import org.junit.Test;

public class SimpleLinkTest {

	@Test
	public void copyConstructorTest() {
		Properties p = new Properties();
		p.put("k1", "v1");
		
		assertEquals("v1", p.getProperty("k1"));
		
		
		SimpleLink link = new SimpleLink(1);
		link.setProperty("k1", "v1");
		link.setProperty("k2", "v2");
		
		
		
		SimpleLink copy = new SimpleLink(link);
		
		
		copy.setProperty("k1", "v3");
		
		assertTrue(link.getValue() == copy.getValue());
		assertEquals(link.getID(), copy.getID());
		assertEquals(link.getProperty("k2"), copy.getProperty("k2"));
		assertFalse(link.getProperty("k1").equals(copy.getProperty("k1")));
		
		
		
		link.setValue(2.0);
		
		assertFalse(link.getValue() == copy.getValue());
		
		copy.setValue(2.0);
		
		assertTrue(link.getValue() == copy.getValue());		
	}
}
