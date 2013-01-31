/**
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * This file is part of the b-have network library.
 * 
 * The b-have network library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The b-have network library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the b-have network library.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
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
