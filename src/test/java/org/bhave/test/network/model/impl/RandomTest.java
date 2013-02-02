package org.bhave.test.network.model.impl;

import static org.junit.Assert.*;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.junit.Test;

public class RandomTest {

	@Test
	public void test() {
		RandomGenerator random = new MersenneTwister();
		int n = random.nextInt(1);
		assertEquals(0, n);
	}

}
