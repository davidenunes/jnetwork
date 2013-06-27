/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhave.network.impl.fast;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bhave.network.NetworkModule;
import org.bhave.network.api.DirectedNetwork;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.api.UndirectedNetwork;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 *
 * @author davide
 */
public class FastNetworkTest {

    private static final Injector injector = Guice
            .createInjector(new NetworkModule());

    @Test
    public void testGetCopy() {
        Network network = injector.getInstance(Network.class);
        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);


        Network networkCopy = network.getCopy();
        assertNotSame(network, networkCopy);


        assertEquals(network.getNodeCount(), networkCopy.getNodeCount());

        Node node1Copy = networkCopy.getNode(node1.getID());

        assertNotNull(node1Copy);
        assertNotSame(node1, node1Copy);
        assertEquals(node1.getNetwork(), network);
        assertNotNull(node1Copy.getNetwork());
        assertEquals(node1Copy.getNetwork(), networkCopy);



    }

    @Test
    public void testAddNode() {
        Network network = injector.getInstance(Network.class);
        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        assertEquals(network.getNodeCount(), 2);

        assertTrue(network.containsNode(node1));

        assertEquals(network.getNode(node1.getID()), node1);
    }

    @Test
    public void testCreateNode() {
        Network network = injector.getInstance(Network.class);
        assertNotNull(network);
        Node node1 = network.createNode();

        //the node was not added yet so the get network should be null
        assertNotSame(node1.getNetwork(), network);

        assertNull(node1.getNetwork());
    }

    @Test
    public void testCreateLink() {
        Network network = injector.getInstance(Network.class);

        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        Link link = network.createLink(node1, node2);
        assertNotNull(link);

        assertSame(link.from(), node1);
        assertSame(link.to(), node2);

        //assertEquals(1, network.getNeighbours(node1).size());

    }

    @Test
    public void testAddLink_Node_Node() {
        Network network = injector.getInstance(Network.class);

        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        Link link = network.addLink(node1, node2);
        assertNotNull(link);

        assertTrue(network.containsLink(link));
        assertEquals(1, network.getLinkCount());

        assertSame(network.getLink(link.getID()), link);
    }

    @Test
    public void testRemoveNode_Node() {
        Network network = injector.getInstance(Network.class);

        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        //create a couple of network copies to experiment
        Network network2 = network.getCopy();

        assertNotSame(network, network2);

        assertEquals(2, network.getNodeCount());

        //test the remove
        network.removeNode(node1);
        assertEquals(1, network.getNodeCount());
        network.addNode(node1);


        network2.addLink(network2.getNode(node1.getID()), network2.getNode(node2.getID()));
        assertEquals(1, network2.getLinkCount());
        assertEquals(0, network.getLinkCount());

        Node node1Copy = network2.getNode(node1.getID());
        Node node2Copy = network2.getNode(node2.getID());

        assertTrue(network2.containsNode(node1Copy));

        assertEquals(1, network2.getNeighbours(node1Copy).size());

        network2.removeNode(network2.getNode(node1.getID()));
        assertFalse(network2.containsNode(node1Copy));

        assertEquals(0, network2.getNeighbours(node2Copy).size());
        assertEquals(0, network2.getLinkCount());

        assertEquals(2, network.getNodeCount());

    }

    @Test
    public void testRemoveNode_int() {
    }

    @Test
    public void testRemoveLink_Link() {
        Network network = injector.getInstance(Network.class);

        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        Link link = network.addLink(node1, node2);
        assertEquals(1, network.getLinkCount());
        network.removeLink(link);
        assertEquals(0, network.getLinkCount());

        assertTrue(network.getNeighbours(node1).isEmpty());
        assertTrue(network.getNeighbours(node2).isEmpty());

    }

    @Test
    public void testRemoveLink_int() {
    }

    @Test
    public void testGetNode() {
    }

    @Test
    public void testGetLink_int() {
        Network network = injector.getInstance(Network.class);

        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        Link link = network.addLink(node1, node2);

        assertSame(link, network.getLink(link.getID()));
    }

    @Test
    public void testGetLink_Node_Node() {
    }

    @Test
    public void testGetLinks_Node() {
        Network network = injector.getInstance(UndirectedNetwork.class);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        Network networkCopy = network.getCopy();

        //Link link = network.createLink(node1, node2);
        //network.addLink()
        Link link = network.addLink(node1, node2);
        assertEquals(1, network.getLinkCount());

        assertSame(network.getLinks(node1).iterator().next(), link);
        assertSame(network.getLinks(node2).iterator().next(), link);


        networkCopy.addLink(networkCopy.createLink(networkCopy.getNode(node1.getID()), networkCopy.getNode(node2.getID())));
        assertEquals(1, networkCopy.getLinkCount());
    }

    @Test
    public void testGetOutLinks() {
        Network network = injector.getInstance(DirectedNetwork.class);

        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        Link link = network.addLink(node1, node2);
        assertEquals(1, network.getLinkCount());

        assertSame(link, network.getOutLinks(node1).iterator().next());

        assertTrue(network.getOutLinks(node2).isEmpty());
    }

    @Test
    public void testGetInLinks() {
        Network network = injector.getInstance(DirectedNetwork.class);

        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        Link link = network.addLink(node1, node2);
        assertEquals(1, network.getLinkCount());

        assertSame(link, network.getInLinks(node2).iterator().next());

        assertTrue(network.getInLinks(node1).isEmpty());
    }

    @Test
    public void testGetSuccessors() {
        Network network = injector.getInstance(DirectedNetwork.class);

        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        Link link = network.addLink(node1, node2);
        assertEquals(1, network.getLinkCount());

        assertSame(node2, network.getSuccessors(node1).iterator().next());

        assertTrue(network.getSuccessors(node2).isEmpty());
    }

    @Test
    public void testGetPredecessors() {
        Network network = injector.getInstance(DirectedNetwork.class);

        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);
        network.addNode(node2);

        Link link = network.addLink(node1, node2);
        assertEquals(1, network.getLinkCount());

        assertSame(node1, network.getPredecessors(node2).iterator().next());

        assertTrue(network.getPredecessors(node1).isEmpty());
    }

    @Test
    public void testGetNeighbours_Node() {
    }

    @Test
    public void testGetNeighbours_int() {
    }

    @Test
    public void testGetNodes_0args() {
    }

    @Test
    public void testGetLinks_0args() {
    }

    @Test
    public void testGetNodes_int() {
    }

    @Test
    public void testGetNodes_Link() {
    }

    @Test
    public void testGetNodeCount() {
    }

    @Test
    public void testGetLinkCount() {
    }

    @Test
    public void testContainsNode() {
        Network network = injector.getInstance(Network.class);
        assertNotNull(network);
        Node node1 = network.createNode();
        Node node2 = network.createNode();

        network.addNode(node1);

        assertTrue(network.containsNode(node1));
        assertFalse(network.containsNode(node2));
    }

    @Test
    public void testContainsLink_Link() {
    }

    @Test
    public void testContainsLink_Node_Node() {
    }

    @Test
    public void testContainsDirectedLink() {
    }
}
