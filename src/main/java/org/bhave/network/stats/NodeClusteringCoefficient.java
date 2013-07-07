/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhave.network.stats;

import java.util.Collection;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;

/**
 *
 * @author davide
 */
public class NodeClusteringCoefficient {

    public static Double getValue(Node node) {
        Network network = node.getNetwork();
        if (network == null) {
            throw new RuntimeException("The given node does not belong to a network");
        }
        double clusteringCoefficient = 0;
        Collection<? extends Node> neighbours = network.getSuccessors(node);
        int pairs = 0;
        //an object must have at least to neighbours to calculate the cc
        if (neighbours.size() > 2) {
            for (Node neighbour : neighbours) {
                Collection<? extends Node> neighboursOfNeigbours =
                        network.getSuccessors(neighbour);
                for (Node nn : neighboursOfNeigbours) {
                    if ((!nn.equals(node))
                            && (!nn.equals(neighbour))
                            && (neighbours.contains(nn))) {

                        pairs++;
                    }
                }
            }
            clusteringCoefficient += (double) pairs / (double) (neighbours.size() * neighbours.size() - 1);
        }


        return clusteringCoefficient;
    }
}
