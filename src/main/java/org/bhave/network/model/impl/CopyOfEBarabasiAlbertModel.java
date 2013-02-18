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
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.BarabasiAlbertModel;

import com.google.inject.Inject;

/**
 * Efficient implementation of Preferential Attachment for the
 * {@link BarabasiAlbertModel}.
 * 
 * @author Davide Nunes
 * 
 */
public class CopyOfEBarabasiAlbertModel implements BarabasiAlbertModel {
	private static final String NUM_NODES_PARAM = "numNodes";
	private static final String MIN_DEGREE_PARAM = "d";
	private static final String SEED_PARAM = "seed";

	// get a default configuration instance the NetworkModule
	private final Configuration config;
	private final RandomGenerator random;
	private final Network network;

	@Inject
	public CopyOfEBarabasiAlbertModel(Configuration config,
			RandomGenerator random, Network network) {
		this.config = config;
		this.random = random;
		this.network = network;

		// setup a default configuration
		this.config.setProperty(NUM_NODES_PARAM, 2);
		this.config.setProperty(MIN_DEGREE_PARAM, 1);
		this.config.setProperty(SEED_PARAM, System.currentTimeMillis());
	}

	@Override
	public Network generate() {
		// get configuration values
		int n = config.getInt(NUM_NODES_PARAM);
		int d = config.getInt(MIN_DEGREE_PARAM);

		// create nodes
		for (int v = 0; v < n; v++) {
			network.addNode(network.createNode());
		}
		// this can be shuffled
		ArrayList<Node> nodes = new ArrayList<>(network.getNodes());

		// pool for node scores
		int[] scores = new int[n];
		int numLinks = 0;

		// add 2 nodes
		network.addLink(nodes.get(0), nodes.get(1));
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
				network.addLink(nodes.get(v), nodes.get(p));
				numLinks++;
				current.add(p);
			}
			scores[v] += i;
			for (Integer e : current) {
				scores[e]++;
			}

		}
		return network;
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
	 * @return a node according to the preferential Attachment mechanism
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
		int d = configuration.getInt(MIN_DEGREE_PARAM);

		if (numNodes < 2 || d < 1) {
			throw new ConfigurationException("numNodes must be >= 2 && d >= 1");
		}

		// configure random number generator
		random.setSeed(configuration.getLong(SEED_PARAM));
	}

}
