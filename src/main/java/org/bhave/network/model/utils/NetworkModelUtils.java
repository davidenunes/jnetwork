/**
 * 
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * This file is part of network-api.
 *
 * network-api is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * The network-api is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with network-api.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.network.model.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;

public class NetworkModelUtils {
	/**
	 * Considering all the possible links of an undirected network, we could
	 * represent the links as an adjacency matrix (ignoring loops) such as:
	 * 
	 * M = [ 01 02 03 04 ] [ 00 12 13 14 ] [ 00 00 23 24 ] [ 00 00 00 34 ]
	 * 
	 * for a network with 5 nodes.
	 * 
	 * This procedure transforms a linear index from a vector of possible edges:
	 * 
	 * [ 01, 02, 03, 04, 12, 13, 14, 23, 24, 34 ]
	 * 
	 * into the node IDS of the respective edge.
	 * 
	 * For Instance, the index i=4 corresponds to the edge (1,2). The procedure
	 * returns (1,2) without constructing a vector with all the links in the
	 * network.
	 * 
	 * @param index
	 *            the index of the link we want to retrieve 0 <= i <
	 *            Binomial(n,2)
	 * @param numNodes
	 *            the number of nodes in the network
	 * 
	 * @return a pair of IDS for the nodes in the link we want to retrieve.
	 */
	public static Pair<Integer, Integer> getLink(int index, int numNodes) {
		long maxI = numNodes - 1;

		long ii = (maxI * (maxI + 1) / 2) - 1 - index;
		double t = FastMath.sqrt(8 * ii + 1);
		long k = (long) FastMath.floor((t - 1) / 2);
		long row = maxI - 1 - k;

		double column = ((index + (row * (row + 1) / 2)) % maxI);

		int node1 = (int) row;
		int node2 = (int) (column + 1);

		return Pair.of(node1, node2);
	}

	/**
	 * Utility used to select a random random (by id) excluding a given vector
	 * of id values. This assumes that all the node IDs are sequential and a
	 * network has at max a given number of nodes.
	 * 
	 * @param random
	 *            a random number generator
	 * @param numNodes
	 *            the max number of nodes
	 * @param exclude
	 *            the IDs to be excluded from the selection
	 * @return
	 */
	public static int getRandomNode(RandomGenerator random, int numNodes, int[] exclude) {
		int r = random.nextInt(numNodes - exclude.length);
		for (int e : exclude) {
			if (r < e) {
				return r;
			}
			r++;
		}
		return r;
	}

}
