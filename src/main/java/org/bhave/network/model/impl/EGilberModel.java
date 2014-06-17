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
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.GilbertModel;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Efficient implementation of {@link GilbertModel}.
 *
 * @author Davide Nunes
 *
 */
public class EGilberModel extends AbstractNetworkModel implements GilbertModel {

    private static final String PARAM_NUM_NODES = P_NUM_NODES;
    private static final String PARA_ATTACH_P = P_P;

    @Inject
    public EGilberModel(Configuration config, RandomGenerator random,
            Provider<Network> networkProvider) {
        super(config, random, networkProvider);
    }

    @Override
    Configuration defaultConfiguration(Configuration config) {
        config.setProperty(PARAM_NUM_NODES, 10);
        config.setProperty(PARA_ATTACH_P, 0.5);
        config.setProperty(PARAM_SEED, System.currentTimeMillis());
        return config;
    }

    @Override
    public void configure(Configuration configuration)
            throws ConfigurationException {
        int numNodes = config.getInt(PARAM_NUM_NODES);
        double p = config.getDouble(PARA_ATTACH_P);

        if (numNodes < 0) {
            throw new ConfigurationException(PARAM_NUM_NODES + " must be >= 0");
        }

        if (p < 0 || p > 1) {
            throw new ConfigurationException(PARA_ATTACH_P
                    + " must be within: 0 < p < 1");
        }
    }

    @Override
    public void generateNetwork() {
        int n = config.getInt(PARAM_NUM_NODES);
        double p = config.getDouble(PARA_ATTACH_P);

        Node[] nodes = new Node[n];
        // add nodes to the network
        for (int i = 0; i < n; i++) {
            Node newNode = network.createNode();
            network.addNode(newNode);
            nodes[i] = newNode;
        }

        int v = 1, w = -1;
        while (v < n) {
            double r = random.nextDouble();
            w += 1 + (FastMath.log(1 - r) / FastMath.log(1 - p));
            while (w >= v && v < n) {
                w = w - v;
                v++;
            }
            if (v < n) {
                network.addLink(nodes[v], nodes[w]);
            }
        }
    }

    @Override
    public void configure(int numNodes, double p, long seed)
            throws ConfigurationException {
        config.setProperty(PARAM_NUM_NODES, numNodes);
        config.setProperty(PARA_ATTACH_P, p);
        config.setProperty(PARAM_SEED, seed);
        this.configure(config);
    }

}
