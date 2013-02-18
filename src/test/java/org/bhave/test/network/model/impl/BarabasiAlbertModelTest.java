/**
 * 
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * This file is part of network-api.
 *
 * network-api is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * The network-api is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with network-api.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.test.network.model.impl;

import static org.junit.Assert.*;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.bhave.network.NetworkModule;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.BAModel;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class BarabasiAlbertModelTest {

	private static final Injector injector = Guice
			.createInjector(new NetworkModule());
	

	@Test
	public void defaultConfigurationTest() {
		BAModel model = injector
				.getInstance(BAModel.class);
		assertNotNull(model);

		Network network = model.generate();
		assertNotNull(network);

		assertEquals(2, network.getNodeCount());
		assertEquals(1, network.getLinkCount());
	}

	@Test
	public void configureTest() {
		BAModel model = injector
				.getInstance(BAModel.class);
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
		int numNodes = 1000;

		BAModel model = injector
				.getInstance(BAModel.class);
		Configuration config = model.getConfiguration();
		config.setProperty("numNodes", numNodes);
		config.setProperty("d", 20);
		config.setProperty("seed", 0);

		model.configure(config);

		Network network = model.generate();
		assertNotNull(network);

		int sumDeg = 0;
		for (Node node : network.getNodes()) {
			int d = network.getNeighbours(node).size();
			System.out.print(d+" ");
			sumDeg += d;
		}
		System.out.println();

		assertEquals(network.getLinkCount() * 2, sumDeg);
	}

}
