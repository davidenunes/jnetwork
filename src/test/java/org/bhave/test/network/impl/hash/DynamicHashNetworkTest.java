package org.bhave.test.network.impl.hash;

import static org.junit.Assert.*;

import org.bhave.network.NetworkModule;
import org.bhave.network.api.DynamicNetwork;
import org.bhave.network.api.Link;
import org.bhave.network.api.Node;
import org.bhave.network.impl.hash.dynamic.DynamicHashNetwork;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class DynamicHashNetworkTest {

	private final Injector injector = Guice.createInjector(new NetworkModule());

	@Test
	public void testCreate() {
		DynamicNetwork network = new DynamicHashNetwork();
		assertNotNull(network);

		assertEquals(0, network.getNodeCount());
		assertEquals(0, network.getLinkCount());

		assertEquals(0, network.getFirstTime());
		assertEquals(0, network.getLastTime());
	}

	@Test
	public void testCreateByGuice() {
		DynamicNetwork network = injector.getInstance(DynamicNetwork.class);
		assertNotNull(network);

		assertEquals(0, network.getNodeCount());
		assertEquals(0, network.getLinkCount());

		assertEquals(0, network.getFirstTime());
		assertEquals(0, network.getLastTime());
	}

	@Test
	public void testSetCurrentTime() {
		//create a dynamic network
		DynamicNetwork network = new DynamicHashNetwork();

		// initial time t = 0
		Node node1 = network.createNode();
		Node node2 = network.createNode();
		Link link1 = network.createLink();

		network.addNode(node1);
		network.addNode(node2);

		network.addLink(node1, node2, link1);

		// time t = 3
		network.setCurrentTime(3);

		// it should contain the same nodes as the nodes are compared by ID
		assertTrue(network.containsNode(node1));
		assertTrue(network.containsNode(node2));
		assertTrue(network.containsLink(link1));

		// but they are copies of the nodes and links of instant t = 0, not the
		// same nodes and links
		assertNotSame(network.getNode(node1.getID()), node1);
		assertNotSame(network.getNode(node2.getID()), node2);
		assertNotSame(network.getLink(link1.getID()), link1);

	}

}
