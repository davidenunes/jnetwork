/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhave.network.stats;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.util.FastMath;
import org.bhave.network.NetworkModule;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.api.UndirectedNetwork;
import org.bhave.network.model.BAModel;
import org.bhave.network.model.KRegularModel;
import org.bhave.network.model.NetworkModel;
import org.bhave.network.model.WSModel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author davide
 */
public class ClusteringCoefficientTest {

    private static final Injector injector = Guice
            .createInjector(new NetworkModule());

    @Test
    public void testGetValue() throws ConfigurationException {
        Network network = null;


        NetworkModel model = injector
                .getInstance(WSModel.class);
        assertNotNull(model);


        ((WSModel) model).configure(300, 10, 0.99, 0);

        network = model.generate();
        assertNotNull(network);

        double cc = NetworkClusteringCoefficient.getValue(network);
        System.out.println("Cluster Coefficient:" + cc);

        model = injector.getInstance(KRegularModel.class);

        Configuration config = model.getConfiguration();
        config.setProperty("k", 50);
        config.setProperty("numNodes", 100);
        model.configure(config);

        network = model.generate();
        assertNotNull(network);

        double cc2 = NetworkClusteringCoefficient.getValue(network);
        System.out.println("Cluster Coefficient Regular:" + cc2);


        ArrayList<Node> nodes = new ArrayList<>(network.getNodes());
        assertTrue(network.getNeighbours(nodes.get(49)).size() == 99);


    }
}
