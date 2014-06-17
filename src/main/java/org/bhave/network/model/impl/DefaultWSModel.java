/**
 *
 */
package org.bhave.network.model.impl;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.KRegularModel;
import org.bhave.network.model.WSModel;
import org.bhave.network.model.utils.NetworkModelUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Davide Nunes
 *
 */
public class DefaultWSModel extends AbstractNetworkModel implements WSModel {

    private static final String PARAM_NUM_NODES = P_NUM_NODES;
    private static final String PARAM_DEGREE = P_D;
    private static final String PARAM_REATTACHP = P_P;
    private final KRegularModel regularModel;

    @Inject
    public DefaultWSModel(Configuration config, RandomGenerator random,
            Provider<Network> networkProvider, KRegularModel regularModel) {
        super(config, random, networkProvider);
        this.regularModel = regularModel;
    }

    @Override
    void generateNetwork() {
        int n = config.getInt(PARAM_NUM_NODES);
        double p = config.getDouble(PARAM_REATTACHP);

        // regular model was already configured and is ready to be used
        network = regularModel.generate();

        ArrayList<Link> links = new ArrayList<>(network.getLinks());

        if (p > 0) {
            for (int l = 0; l < links.size(); l++) {
                double r = random.nextDouble();
                // re-wire the current link
                if (r < p) {
                    Pair<Node, Node> nodes = links.get(l).nodes();
                    // get a random node excluding its current neighbours

                    ArrayList<Integer> toExclude = new ArrayList<>();
                    // the first node is to be excluded
                    toExclude.add(nodes.getLeft().getID());
                    for (Node neighbour : network
                            .getNeighbours(nodes.getLeft())) {
                        toExclude.add(neighbour.getID());
                    }
                    Collections.sort(toExclude);

                    int[] exclude = new int[toExclude.size()];
                    for (int i = 0; i < toExclude.size(); i++) {
                        exclude[i] = toExclude.get(i);
                    }
                    int newPartner = NetworkModelUtils.getRandomNode(random, n,
                            exclude);

                    network.removeLink(links.get(l));
                    network.addLink(nodes.getLeft(),
                            network.getNode(newPartner));
                }
            }
        }

    }

    @Override
    public void configure(Configuration configuration)
            throws ConfigurationException {
        int numNodes = configuration.getInt(PARAM_NUM_NODES);
        int d = configuration.getInt(PARAM_DEGREE);
        double p = configuration.getDouble(PARAM_REATTACHP);
        long seed = config.getLong(PARAM_SEED);

        if (numNodes < 0) {
            throw new ConfigurationException(PARAM_NUM_NODES
                    + "must be positive");
        }

        if (d < 1 || d > ((numNodes - 1) / 2)) {
            throw new ConfigurationException(PARAM_DEGREE
                    + "must be within: 1 <= d <= ((n-1)/ 2)");
        }
        if (p < 0 || p >= 1) {
            throw new ConfigurationException(PARAM_REATTACHP
                    + "must be within: 0 <= p < 1");
        }

        // also configures the kRegular network model
        regularModel.configure(numNodes, d, seed);

    }

    @Override
    Configuration defaultConfiguration(Configuration config) {
        config.setProperty(PARAM_NUM_NODES, 10);
        config.setProperty(PARAM_DEGREE, 2);
        config.setProperty(PARAM_REATTACHP, 0.2);
        config.setProperty(PARAM_SEED, System.currentTimeMillis());
        return config;
    }

    @Override
    public void configure(int numNodes, int k, double p, long seed)
            throws ConfigurationException {

        this.config.setProperty(PARAM_NUM_NODES, numNodes);
        this.config.setProperty(PARAM_DEGREE, k);
        this.config.setProperty(PARAM_REATTACHP, p);
        this.config.setProperty(PARAM_SEED, seed);

        configure(this.config);

    }
}
