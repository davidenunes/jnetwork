package org.bhave.test.network.model.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.bhave.network.NetworkModule;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.BarabasiAlbertModel;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class BarabasiAlbertModelTest {

	private static final Injector injector = Guice
			.createInjector(new NetworkModule());

	@Test
	public void defaultConfigurationTest() {
		BarabasiAlbertModel model = injector
				.getInstance(BarabasiAlbertModel.class);
		assertNotNull(model);

		Network network = model.generate();
		assertNotNull(network);

		assertEquals(2, network.getNodeCount());
		assertEquals(1, network.getLinkCount());
	}

	@Test
	public void configureTest() {
		BarabasiAlbertModel model = injector
				.getInstance(BarabasiAlbertModel.class);
		assertNotNull(model);

		Configuration config = model.getConfiguration();
		assertNotNull(config);
		assertTrue(config.containsKey("numNodes"));
		assertTrue(config.containsKey("seed"));

		config.setProperty("numNodes", -2);

		boolean configFail = false;
		try {
			model.configure(config);
		} catch (ConfigurationException e) {
			configFail = true;
		}
		assertTrue(configFail);

		config.setProperty("numNodes", 100);
		config.setProperty("seed", System.currentTimeMillis());

		try {
			model.configure(config);
		} catch (ConfigurationException e) {
			fail();
		}
	}

	@Test
	public void generateTest() throws ConfigurationException {
		int numNodes = 2000;

		BarabasiAlbertModel model = injector
				.getInstance(BarabasiAlbertModel.class);
		Configuration config = model.getConfiguration();
		config.setProperty("numNodes", numNodes);
		config.setProperty("d", 1);
		config.setProperty("seed", 0);

		model.configure(config);

		Network network = model.generate();


		for(Link link : network.getLinks()){
			System.out.println(network.getNodes(link));
		}

		List<Integer> degList = new ArrayList<Integer>();
		for (Node node : network.getNodes()) {
			degList.add(network.getNeighbours(node).size());
		}

		Collections.sort(degList);

		int sum = 0;
		for (Integer d : degList) {
			sum += d;
		}

		assertEquals(sum, network.getLinkCount()*2);

	}

}
