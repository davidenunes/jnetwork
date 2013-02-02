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

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.BarabasiAlbertModel;
import com.google.inject.Inject;

/**
 * Naive Default Implementation for the {@link BarabasiAlbertModel} interface.
 * This implementation recomputes a cumulative distribution to select nodes
 * according to the preferential attachment mechanism which is highly
 * inefficient. Although it is the most common implementation among existing
 * libraries.
 * 
 * @author Davide Nunes
 * 
 */
public class DefaultBarabasiAlbertModel implements BarabasiAlbertModel {
	private static final String NUM_NODES_PARAM = "numNodes";
	private static final String MINIMUM_DEGREE_PARAM = "d";
	private static final String SEED_PARAM = "seed";

	// get a default configuration instance the NetworkModule
	private final Configuration config;
	private final RandomGenerator random;
	private final Network network;

	@Inject
	public DefaultBarabasiAlbertModel(Configuration config,
			RandomGenerator random, Network network) {
		this.config = config;
		this.random = random;
		this.network = network;

		// setup a default configuration
		this.config.setProperty(NUM_NODES_PARAM, 2);
		this.config.setProperty(MINIMUM_DEGREE_PARAM, 1);
		this.config.setProperty(SEED_PARAM, System.currentTimeMillis());
	}

	@Override
	public Network generate() {
		// get configuration values
		int numNodes = config.getInt(NUM_NODES_PARAM);

		// create a network

		// add first 2 nodes and link them
		Node n1 = network.createNode();
		Node n2 = network.createNode();
		network.addNode(n1);
		network.addNode(n2);
		network.addLink(n1, n2);

		// start preferential attachment growth
		// while the network did not reach the intended number of nodes
		while (network.getNodeCount() != numNodes) {
			// create a new node and add it
			Node newNode = network.createNode();
			network.addNode(newNode);

			// select a preferential partner
			Node partner = getPrefAttach(newNode, network);

			// connect these two nodes
			network.addLink(newNode, partner);
		}

		return network;
	}

	/**
	 * Returns a node according to the preferential attachment method
	 * 
	 * @param exclude
	 *            Collection<N> nodes to be excluded from the algorithm (to
	 *            avoid connection repetition)
	 * 
	 * @return aNode N a node according to preferential attachment method
	 */
	private Node getPrefAttach(Node target, Network network) {

		HashSet<Node> otherNodes = new HashSet<>();
		otherNodes.addAll(network.getNodes());
		otherNodes.remove(target);

		ArrayList<Node> nodes = new ArrayList<>(otherNodes);

		// compute the cumulative distribution function
		double[] cdf = new double[nodes.size()];

		cdf[0] = getAttachmentProb(nodes.get(0), network);
		for (int i = 1; i < nodes.size(); i++) {
			cdf[i] = cdf[i - 1] + getAttachmentProb(nodes.get(i), network);
		}

		double r = random.nextDouble() * cdf[cdf.length - 1];

		for (int i = 0; i < nodes.size(); i++)
			if (r < cdf[i])
				return nodes.get(i);

		// r == 1
		return nodes.get(nodes.size() - 1);
	}

	/**
	 * Returns the probability of attachment to the given node
	 * 
	 * <code>probConnectTo(node) = (degree(node) / sum(all degrees on network))</code>
	 * 
	 * @param node
	 *            the node we want to get the probability of attachment from
	 * @param network
	 *            the network where the node belongs
	 * @return a probability of attachment
	 * 
	 */
	private double getAttachmentProb(Node node, Network network) {
		double ki = network.getNeighbours(node).size();
		return ki / (network.getLinkCount() * 2);
	}

	@Override
	public void configure(int numNodes, int d, long seed)
			throws ConfigurationException {
		config.setProperty(NUM_NODES_PARAM, numNodes);
		config.setProperty(SEED_PARAM, seed);
		this.configure(config);
	}

	@Override
	public Configuration getConfiguration() {
		return this.config;
	}

	@Override
	public void configure(Configuration configuration)
			throws ConfigurationException {
		int numNodes = configuration.getInt(NUM_NODES_PARAM);

		if (numNodes < 2) {
			throw new ConfigurationException("numNodes must be >= 2");
		}

		// configure random number generator
		random.setSeed(configuration.getLong(SEED_PARAM));
	}

}
