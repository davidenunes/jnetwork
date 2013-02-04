package org.bhave.test.network.model.utils;

import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.Pair;
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
	public void limitsTest(){
		int numNodes = 100000;
		int index = 0;
		
		
		long maxI = numNodes - 1;
		
		long ii = (maxI * (maxI + 1) / 2) - 1 - index;
		System.out.println("ii: "+ii);
		double t = FastMath.sqrt(8 * ii + 1);
		System.out.println("t: "+t);
		long k = (long) FastMath.floor((t - 1) / 2);
		System.out.println("k: "+k);
		long row = maxI - 1 - k;

		double column = ((index + (row * (row + 1) / 2)) % maxI);

		int node1 = (int) row;
		int node2 = (int) (column + 1);

		System.out.println("("+node1+", "+node2+")");
		
	}

}
