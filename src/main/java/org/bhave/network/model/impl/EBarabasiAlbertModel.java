package org.bhave.network.model.impl;

import java.util.ArrayList;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.BarabasiAlbertModel;

import com.google.inject.Inject;

public class EBarabasiAlbertModel implements BarabasiAlbertModel {
	private static final String NUM_NODES_PARAM = "numNodes";
	private static final String MINIMUM_DEGREE_PARAM = "d";
	private static final String SEED_PARAM = "seed";

	// get a default configuration instance the NetworkModule
	private final Configuration config;
	private final RandomGenerator random;
	private final Network network;

	@Inject
	public EBarabasiAlbertModel(Configuration config, RandomGenerator random,
			Network network) {
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
		int n = config.getInt(NUM_NODES_PARAM);
		int d = config.getInt(MINIMUM_DEGREE_PARAM);

		// pool representing the links
		int[] linkPool = new int[2 * (n-1)];

		// seed network with 1 connection
		linkPool[0] = 0;
		linkPool[1] = 1;

		int numLinks = 1;

		for (int v = 2; v < n; v++) {
			int index = 2 * numLinks;
			linkPool[index] = v;
			int r = random.nextInt(index);
			linkPool[index + 1] = linkPool[r];

			numLinks++;
		}

		// create nodes
		for (int v = 0; v < n; v++) {
			network.addNode(network.createNode());
		}
		ArrayList<Node> nodes = new ArrayList<>(network.getNodes());

		// add links
		for (int i = 0; i < linkPool.length; i+=2) {
	
			Node node1 = nodes.get(linkPool[i]);
			Node node2 = nodes.get(linkPool[i + 1]);
			network.addLink(node1, node2);
		}
		
		
		return network;
	}

	@Override
	public void configure(int numNodes, int d, long seed)
			throws ConfigurationException {
		config.setProperty(NUM_NODES_PARAM, numNodes);
		config.setProperty(MINIMUM_DEGREE_PARAM, d);
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
		int d = configuration.getInt(MINIMUM_DEGREE_PARAM);

		if (numNodes < 2 || d < 1) {
			throw new ConfigurationException("numNodes must be >= 2");
		}

		// configure random number generator
		random.setSeed(configuration.getLong(SEED_PARAM));
	}

}
