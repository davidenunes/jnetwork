package org.bhave.test.network.model.impl;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

public class TestUniformRandom {
	@Test
	public void testRandom() {
		Pair<Integer, Integer> pair1 = Pair.of(1, 2);
		Pair<Integer, Integer> pair2 = Pair.of(2, 1);
		
	
			
		assertEquals(pair1, pair2);
		
	}
}
