package org.bhave.test.network.model.impl;

import static org.junit.Assert.*;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.bhave.network.NetworkModule;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.model.ERModel;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ERModelTest {
	private static final Injector injector = Guice
			.createInjector(new NetworkModule());

	@Test
	public void generateTest() {
		ERModel model = injector.getInstance(ERModel.class);
		Configuration config = model.getConfiguration();
		config.setProperty("numNodes", 3);
		config.setProperty("numLinks", 3);

		try {
			model.configure(config);
		} catch (ConfigurationException e) {

			fail("configuration failed: " + e.getMessage());
		}

		Network network = model.generate();

		for (Link link : network.getLinks()) {
			System.out.println(network.getNodes(link));
		}

	}

}
