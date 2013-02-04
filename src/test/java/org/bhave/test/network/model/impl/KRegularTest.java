package org.bhave.test.network.model.impl;

import static org.junit.Assert.*;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.bhave.network.NetworkModule;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.KRegularModel;
import org.bhave.network.model.NetworkModel;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class KRegularTest {

	private static final Injector injector = Guice
			.createInjector(new NetworkModule());

	@Test
	public void generateDefaultTest() {
		NetworkModel model = injector.getInstance(KRegularModel.class);

		Network network = model.generate();
		assertNotNull(network);
	}

	@Test
	public void configAndGenerateTest() {
		NetworkModel model = injector.getInstance(KRegularModel.class);

		Configuration config = model.getConfiguration();
		config.setProperty("k", 10);
		config.setProperty("numNodes", 100);

		try {
			model.configure(config);
		} catch (ConfigurationException e) {
			fail("configuration failled");
		}

		Network network = model.generate();
		assertNotNull(network);

		int sumDeg = 0;
		for (Node node : network.getNodes()) {
			int d = network.getNeighbours(node).size();
			assertEquals(20, d);
			sumDeg += d;
		}
		assertEquals(2*network.getLinkCount(), sumDeg);

	}

}
