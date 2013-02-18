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
package org.bhave.network.model.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.BAModel;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Efficient implementation of Preferential Attachment for the
 * {@link DefaultBAModel}.
 * 
 * @author Davide Nunes
 * 
 */
public class DefaultBAModel extends AbstractNetworkModel implements BAModel {
	private static final String PARAM_NUM_NODES = "numNodes";
	private static final String PARAM_MIN_DEG = "d";

	@Inject
	public DefaultBAModel(Configuration config, RandomGenerator random,
			Provider<Network> networkProvider) {
		super(config, random, networkProvider);

	}

	@Override
	public void generateNetwork() {
		// get configuration values
		int n = config.getInt(PARAM_NUM_NODES);
		int d = config.getInt(PARAM_MIN_DEG);

		Node[] nodes = new Node[n];
		// add nodes to the network
		for (int i = 0; i < n; i++) {
			Node newNode = network.createNode();
			network.addNode(newNode);
			nodes[i] = newNode;
		}

		// use the existing random number generator to shuffle our nodeArray
		RandomDataGenerator randomPerm = new RandomDataGenerator(random);
		int[] perm = randomPerm.nextPermutation(n, n);
		// from now on instead of using nodes[i] you use nodes[perm[i]] to get
		// the node indicated by the permutation

		// pool for node scores
		int[] scores = new int[n];
		int numLinks = 0;

		// add 2 nodes
		network.addLink(nodes[perm[0]], nodes[perm[1]]);
		scores[0] = 1;
		scores[1] = 1;
		numLinks = 1;

		// add the rest of the nodes
		for (int v = 2; v < n; v++) {

			// current nodes involved
			HashSet<Integer> current = new HashSet<>();

			int maxLimit = numLinks * 2;
			int i = 0;
			// add d nodes
			for (i = 0; i < d && i < v; i++) {
				int tempMaxLimit = maxLimit;
				for (Integer e : current) {
					tempMaxLimit -= scores[e];
				}
				int r = random.nextInt(tempMaxLimit);

				int p = preferentialAttachment(scores, r, tempMaxLimit, current);
				network.addLink(nodes[perm[v]], nodes[perm[p]]);
				numLinks++;
				current.add(p);
			}
			scores[v] += i;
			for (Integer e : current) {
				scores[e]++;
			}

		}
	}

	/**
	 * Select Partner according to a cumulative distribution of the node scores
	 * 
	 * @param scores
	 *            node degree scores
	 * @param r
	 *            a random number uniformly selected between 0 and the sum of
	 *            all node degrees
	 * @param maxScore
	 * 
	 * @param exclude
	 * 
	 * @return a node according to the preferential attachment mechanism
	 */
	private int preferentialAttachment(int[] scores, int r, int maxScore,
			Set<Integer> exclude) {

		int currentScore = 0;
		int i = 0;
		do {
			// skip excluded
			if (!exclude.contains(i)) {
				currentScore += scores[i];
				if (r < currentScore) {
					return i;
				}
			}
			i++;
		} while (currentScore < maxScore);

		return -1;
	}

	@Override
	public void configure(int numNodes, int d, long seed)
			throws ConfigurationException {
		config.setProperty(PARAM_NUM_NODES, numNodes);
		config.setProperty(PARAM_SEED, seed);
		config.setProperty(PARAM_MIN_DEG, d);
		this.configure(config);
	}

	@Override
	public Configuration getConfiguration() {
		return this.config;
	}

	@Override
	public void configure(Configuration configuration)
			throws ConfigurationException {
		int numNodes = configuration.getInt(PARAM_NUM_NODES);
		int d = configuration.getInt(PARAM_MIN_DEG);

		if (numNodes < 2 || d < 1) {
			throw new ConfigurationException(PARAM_NUM_NODES
					+ " must be >= 2 && d >= 1");
		}
	}

	@Override
	Configuration defaultConfiguration(Configuration config) {
		config.setProperty(PARAM_NUM_NODES, 2);
		config.setProperty(PARAM_MIN_DEG, 1);
		config.setProperty(PARAM_SEED, System.currentTimeMillis());
		return config;
	}

}
