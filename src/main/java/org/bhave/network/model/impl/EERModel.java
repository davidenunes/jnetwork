package org.bhave.network.model.impl;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.ERModel;
import org.bhave.network.model.utils.NetworkModelUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Efficient Implementation for the {@link ERModel}.
 *
 *
 * @author Davide Nunes
 *
 */
public class EERModel extends AbstractNetworkModel implements ERModel {

    public static final String PARAM_NUM_NODES = "numNodes";
    public static final String PARAM_NUM_LINKS = "numLinks";

    @Inject
    public EERModel(Configuration config,
            RandomGenerator random,
            Provider<Network> networkProvider) {
        super(config, random, networkProvider);
    }

    @Override
    Configuration defaultConfiguration(Configuration config) {
        config.setProperty(PARAM_NUM_NODES, 0);
        config.setProperty(PARAM_NUM_LINKS, 0);
        config.setProperty(PARAM_SEED, System.currentTimeMillis());
        return config;
    }

    @Override
    public void generateNetwork() {
        int n = config.getInt(PARAM_NUM_NODES);
        int m = config.getInt(PARAM_NUM_LINKS);

        Node[] nodes = new Node[n];
        // add nodes to the network
        for (int i = 0; i < n; i++) {
            Node newNode = network.createNode();
            network.addNode(newNode);
            nodes[i] = newNode;
        }

        for (int i = 0; i < m; i++) {
            int r = 0;
            Node node1 = null;
            Node node2 = null;
            do {
                // n * (n-1) / 2 = number of possible links
                r = random.nextInt(n * (n - 1) / 2);
                Pair<Integer, Integer> link = NetworkModelUtils.getLink(r, n);
                node1 = nodes[link.getLeft()];
                node2 = nodes[link.getRight()];
            } while (network.containsLinks(node1, node2));

            network.addLink(node1, node2);
        }
        // done
    }

    @Override
    public void configure(int numNodes, int numEdges, long seed)
            throws ConfigurationException {
        config.setProperty(PARAM_NUM_NODES, numNodes);
        config.setProperty(PARAM_NUM_LINKS, numEdges);
        config.setProperty(PARAM_SEED, seed);
        this.configure(config);

    }

    @Override
    public void configure(Configuration configuration)
            throws ConfigurationException {
        int numNodes = config.getInt(PARAM_NUM_NODES);
        int numLinks = config.getInt(PARAM_NUM_LINKS);

        long maxNumLink = numNodes * (numNodes - 1) / 2;

        if (numNodes < 0) {
            throw new ConfigurationException(PARAM_NUM_NODES + " must be >= 0");
        }
        if (numLinks < 0 || numLinks > maxNumLink) {
            throw new ConfigurationException(PARAM_NUM_LINKS
                    + " must be within: 0 <= m <= n*(n-1)/2");

        }

    }
}
