package org.bhave.test.network.model.impl;

import static org.junit.Assert.*;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.tuple.Pair;
import org.bhave.network.NetworkModule;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.WSModel;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class WSTest {

	private static final Injector injector = Guice
			.createInjector(new NetworkModule());

	@Test
	public void testBasicGenerate() {
		WSModel model = injector.getInstance(WSModel.class);

		int numNodes = 10000;
		int k = 2;
		double p = 0.01;
		long seed = 0;

		try {
			model.configure(numNodes, k, p, seed);
		} catch (ConfigurationException e) {
			fail(e.getMessage());
		}

		Network network = model.generate();
		assertNotNull(network);
		
		
		for(Link l : network.getLinks()){
			Pair<Node, Node> nodes = network.getNodes(l);
			System.out.println(nodes);
		}

	}

}
