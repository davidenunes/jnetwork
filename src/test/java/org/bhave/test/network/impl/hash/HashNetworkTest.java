/**
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * This file is part of the b-have network library.
 * 
 * The b-have network library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The b-have network library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the b-have network library.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.test.network.impl.hash;

import static org.junit.Assert.*;

import java.util.Collection;

import org.bhave.network.NetworkModule;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;


public class HashNetworkTest {
	private final Injector injector = Guice.createInjector(new NetworkModule());

	@Test
	public void createEmptyNetwork() {
		// System.out.println("Test createEmptyNetwork:");


		Network network = injector.getInstance(Network.class);
		assertNotNull(network);

		assertEquals(0, network.getNodeCount());
		assertEquals(0, network.getLinkCount());

	}

	@Test
	public void createNewNodesAndLinks() {
		// System.out.println("Test createNewNodesAndLinks:");
		Network network = injector.getInstance(Network.class);
		assertNotNull(network);

		Node node = network.createNode();
		Link link = network.createLink();

		assertNotNull(node);
		assertNotNull(link);

		assertEquals(0, node.getID());
		assertEquals(0, link.getID());

		node = network.createNode();
		link = network.createLink();

		assertEquals(1, node.getID());
		assertEquals(1, link.getID());

	}

	@Test
	public void addRemoveNodesAndLinks() {
		// System.out.println("Test addRemoveNodesAndLinks:");
		Network network = injector.getInstance(Network.class);
		assertNotNull(network);

		Node node1 = network.createNode();
		Node node2 = network.createNode();

		Link link = network.createLink();

		network.addNode(node1);
		network.addNode(node2);

		assertEquals(2, network.getNodeCount());

		network.addLink(node1, node2, link);

		assertEquals(1, network.getLinkCount());

		network.removeLink(link);

		assertEquals(0, network.getLinkCount());

		network.removeNode(node1);

		network.removeNode(node2);

		assertEquals(0, network.getNodeCount());

		// remove node with existing link
		network.addNode(node1);
		network.addNode(node2);
		network.addLink(node1, node2, link);

		network.removeNode(node1);

		assertEquals(0, network.getLinkCount());
		assertEquals(1, network.getNodeCount());

	}

	@Test
	public void testGetLinks() {
		// System.out.println("Test testGetLinks:");
		Network network = injector.getInstance(Network.class);
		assertNotNull(network);

		Node node1 = network.createNode();
		Node node2 = network.createNode();
		network.addNode(node1);
		network.addNode(node2);

		Link link = network.addLink(node1, node2);

		// test get in and out links
		Collection<? extends Link> inLinks = network.getInLinks(node1);
		assertTrue(inLinks.isEmpty());

		Collection<? extends Link> outLinks = network.getOutLinks(node1);
		assertFalse(outLinks.isEmpty());

		// test get links
		Collection<? extends Link> allLinks = network.getLinks(node1);
		assertEquals(1, allLinks.size());
		assertEquals(link, allLinks.iterator().next());

		Collection<? extends Node> succ = network.getSuccessors(node1);
		assertFalse(succ.isEmpty());
		Node succNode = succ.iterator().next();

		assertEquals(succNode, node2);

		Collection<? extends Node> pred = network.getPredecessors(node2);
		assertFalse(pred.isEmpty());
		Node predNode = pred.iterator().next();

		assertEquals(predNode, node1);

	}

	@Test
	public void testGetNeighbours() {
		// System.out.println("Test testGetLinks:");
		Network network = injector.getInstance(Network.class);
		assertNotNull(network);

		Node node1 = network.createNode();
		Node node2 = network.createNode();

		network.addNode(node1);
		network.addNode(node2);
		
		network.addLink(node1, node2);
		
		Collection<? extends Node> neighbours = network.getNeighbours(node1);
		assertFalse(neighbours.isEmpty());
		assertEquals(1, neighbours.size());
		
		assertEquals(node2, neighbours.iterator().next());

	}

}
