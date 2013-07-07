package org.bhave.network.stats;

import java.util.ArrayList;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;

/**
 *
 * @author Davide Nunes
 */
public class NetworkClusteringCoefficient {

    public static Double getValue(Network network) {
        double clusteringCoefficient = 0;
        int N = network.getNodeCount();
        ArrayList<Node> nodes = new ArrayList<>(network.getNodes());


        for (int i = 0; i < N; i++) {
            clusteringCoefficient += NodeClusteringCoefficient.getValue(nodes.get(i));
        }

        return clusteringCoefficient / (double) N;
    }
}
