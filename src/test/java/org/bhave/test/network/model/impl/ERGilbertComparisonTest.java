package org.bhave.test.network.model.impl;

import static org.junit.Assert.*;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.bhave.network.NetworkModule;
import org.bhave.network.api.Network;
import org.bhave.network.model.ERModel;
import org.bhave.network.model.GilbertModel;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ERGilbertComparisonTest {

	private static final Injector injector = Guice
			.createInjector(new NetworkModule());

	@Test
	public void compareTest() {
		int numNodes = 10;
		int numLinks = 5;
		double p = 0.5;
		
		GilbertModel gilbert = injector.getInstance(GilbertModel.class);

		Configuration config = gilbert.getConfiguration();
		config.setProperty("numNodes", numNodes);
		config.setProperty("p",p);

		try {
			gilbert.configure(config);
		} catch (ConfigurationException e) {
			fail("Configuration Failed");
		}

		Network gNetwork = gilbert.generate();

		ERModel er = injector.getInstance(ERModel.class);
		Configuration erConfig = er.getConfiguration();
		erConfig.setProperty("numNodes", numNodes);
		erConfig.setProperty("numLinks", numLinks);

		try {
			er.configure(erConfig);
		} catch (ConfigurationException e) {

			fail("configuration failed: " + e.getMessage());
		}

		Network erNetwork = er.generate();
		
		System.out.println(gNetwork.getLinkCount());
		System.out.println(erNetwork.getLinkCount());
	}

}
