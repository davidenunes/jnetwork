package org.bhave.test.network.model.random;

import static org.junit.Assert.*;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.junit.Test;

public class RandomTest {

	@Test
	public void test() {
		RandomGenerator random = new MersenneTwister();
		int numGen = 1000;

		random.setSeed(0);

		int v1[] = new int[numGen];
		int v2[] = new int[numGen];

		for (int i = 0; i < v1.length; i++) {
			v1[i] = random.nextInt(100);
		}

		for (int i = 0; i < v2.length; i++) {
			v2[i] = random.nextInt(100);
		}

		assertFalse(ArrayUtils.isEquals(v1, v2));

		random.setSeed(0);

		for (int i = 0; i < v2.length; i++) {
			v2[i] = random.nextInt(100);
		}

		assertTrue(ArrayUtils.isEquals(v1, v2));

	}

}
