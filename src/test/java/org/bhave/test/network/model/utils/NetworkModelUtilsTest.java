package org.bhave.test.network.model.utils;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.bhave.network.model.utils.NetworkModelUtils;
import org.junit.Test;

public class NetworkModelUtilsTest {

	@Test
	public void test() {
		Pair<Integer, Integer> p = NetworkModelUtils.getLink(7, 5);

		assertEquals(Pair.of(2, 3), p);
	}

	@Test
	public void limitsTest() {
		int numNodes = 100000;
		int index = 0;

		long maxI = numNodes - 1;

		long ii = (maxI * (maxI + 1) / 2) - 1 - index;

		double t = FastMath.sqrt(8 * ii + 1);

		long k = (long) FastMath.floor((t - 1) / 2);

		long row = maxI - 1 - k;

		double column = ((index + (row * (row + 1) / 2)) % maxI);

		int node1 = (int) row;
		int node2 = (int) (column + 1);

		assertEquals(0, node1);
		assertEquals(1, node2);
	}

	@Test
	public void testRandomNode() {
		RandomGenerator random = new MersenneTwister(0);
		HashMap<Integer, Integer> count = new HashMap<>();
		int numNodes = 10;
		int[] exclude = { 1, 2, 3, 4, 5};

		for (int i = 0; i < 100000; i++) {
			int randomN = NetworkModelUtils.getRandomNode(random, numNodes,
					exclude);
			if (count.containsKey(randomN)) {
				count.put(randomN, (count.get(randomN) + 1));
			} else {
				count.put(randomN, 1);
			}
			
			for (int e : exclude) {
				assertNotSame(e, randomN);
			}
		}
		for(int k: count.keySet()){
			System.out.println("node: "+k+" c: "+count.get(k));
		}
	}

}
