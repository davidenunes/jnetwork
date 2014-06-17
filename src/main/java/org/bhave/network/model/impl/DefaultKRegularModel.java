/**
 *
 * Copyright 2013 Davide Nunes Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * This file is part of network-api.
 *
 * network-api is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * The network-api is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * network-api. If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.network.model.impl;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.KRegularModel;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Implementation of {@link KRegularModel}
 * 
 * @author Davide Nunes
 */
public class DefaultKRegularModel extends AbstractNetworkModel implements
		KRegularModel {

	private static final String K_PARAM = P_K;
	private static final String NUM_NODES_PARAM = P_NUM_NODES;

	@Inject
	public DefaultKRegularModel(Configuration config, RandomGenerator random,
			Provider<Network> networkProvider) {
		super(config, random, networkProvider);
	}

	@Override
	Configuration defaultConfiguration(Configuration config) {
		config.setProperty(NUM_NODES_PARAM, 3);
		config.setProperty(K_PARAM, 1);
		config.setProperty(PARAM_SEED, System.currentTimeMillis());
		return config;
	}

	@Override
	public void configure(Configuration configuration)
			throws ConfigurationException {

		int numNodes = configuration.getInt(NUM_NODES_PARAM);
		int k = configuration.getInt(K_PARAM);

		if (numNodes < 0) {
			throw new ConfigurationException(NUM_NODES_PARAM + " must be > 0");
		}

		if (k < 1 || k > (numNodes / 2)) {
			throw new ConfigurationException(K_PARAM
					+ " must be within 1 <= k <= (" + NUM_NODES_PARAM + ") / 2");
		}

	}

	@Override
	public void generateNetwork() {

		int numNodes = config.getInt(NUM_NODES_PARAM);
		int k = config.getInt(K_PARAM);

		Node[] nodes = new Node[numNodes];
		// add nodes to the network
		for (int i = 0; i < numNodes; i++) {
			Node newNode = network.createNode();
			network.addNode(newNode);
			nodes[i] = newNode;
		}

		// use the existing random number generator to shuffle our nodeArray
		RandomDataGenerator randomPerm = new RandomDataGenerator(random);
		int[] perm = randomPerm.nextPermutation(numNodes, numNodes);

		// create the regular network
		for (int i = 0; i < numNodes; i++) {
			int j = 1;
			// add links to the next k neighbours without duplicated links
			while (j <= k) {
				Node n1 = nodes[perm[i]];
				Node n2 = nodes[perm[(i + j) % numNodes]];

				if (!network.containsLinks(n1, n2)) {
					network.addLink(n1, n2);
				}
				j++;
			}
		}

	}

	@Override
	public void configure(int numNodes, int k, long seed)
			throws ConfigurationException {
		config.setProperty(NUM_NODES_PARAM, numNodes);
		config.setProperty(K_PARAM, k);
		config.setProperty(PARAM_SEED, seed);
		this.configure(config);
	}
}
