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

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.BAForestModel;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Efficient implementation of Preferential Attachment for the
 * {@link DefaultBAForestModel}.
 * 
 * @author Davide Nunes
 * 
 */
public class DefaultBAForestModel extends AbstractNetworkModel implements
		BAForestModel {
	private static final String PARAM_NUM_NODES = "numNodes";

	@Inject
	public DefaultBAForestModel(Configuration config,
			RandomGenerator random,
			Provider<Network> networkProvider) {
		super(config, random, networkProvider);
	}

	/**
	 * Return a default configuration for this model
	 */
	@Override
	Configuration defaultConfiguration(Configuration config) {
		config.setProperty(PARAM_NUM_NODES, 2);
		config.setProperty(PARAM_SEED, System.currentTimeMillis());
		return config;
	}

	@Override
	public void generateNetwork() {
		resetModel();
		// get configuration values
		int n = config.getInt(PARAM_NUM_NODES);

		// pool representing the links
		int[] linkPool = new int[2 * (n - 1)];

		// seed network with 1 connection
		linkPool[0] = 0;
		linkPool[1] = 1;

		int numLinks = 1;

		// create the other edges
		for (int v = 2; v < n; v++) {
			int index = 2 * numLinks;

			linkPool[index] = v;
			int r = random.nextInt(index);
			linkPool[index + 1] = linkPool[r];
			numLinks++;
		}

		Node[] nodes = new Node[n];
		// add nodes to the network
		for (int i = 0; i < n; i++) {
			Node newNode = network.createNode();
			network.addNode(newNode);
			nodes[i] = newNode;
		}
		// use the existing random number generator to shuffle our nodes
		RandomDataGenerator randomPerm = new RandomDataGenerator(random);
		int[] perm = randomPerm.nextPermutation(n, n);

		// add links to the Network
		for (int i = 0; i < linkPool.length; i += 2) {
			Node node1 = nodes[perm[linkPool[i]]];

			Node node2 = nodes[perm[linkPool[i + 1]]];
			network.addLink(node1, node2);
		}
	}

	@Override
	public void configure(int numNodes, long seed)
			throws ConfigurationException {
		config.setProperty(PARAM_NUM_NODES, numNodes);
		config.setProperty(PARAM_SEED, seed);
		this.configure(config);
	}

	@Override
	public void configure(Configuration configuration)
			throws ConfigurationException {
		int numNodes = configuration.getInt(PARAM_NUM_NODES);

		if (numNodes < 2) {
			throw new ConfigurationException("numNodes must be >= 2");
		}
	}

}
