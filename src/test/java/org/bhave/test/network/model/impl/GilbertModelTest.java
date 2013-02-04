package org.bhave.test.network.model.impl;

import static org.junit.Assert.*;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.bhave.network.NetworkModule;
import org.bhave.network.api.Network;
import org.bhave.network.model.GilbertModel;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GilbertModelTest {

	private static final Injector injector = Guice
			.createInjector(new NetworkModule());

	@Test
	public void testGenerateDefault() {
		GilbertModel model = injector.getInstance(GilbertModel.class);
		Network network = model.generate();
		assertNotNull(network);
	}
	
	@Test
	public void testGenerate() {
		GilbertModel model = injector.getInstance(GilbertModel.class);
		
		
		Configuration config = model.getConfiguration();
		config.setProperty("numNodes", 100);
		config.setProperty("p", 0.5);
		
		try {
			model.configure(config);
		} catch (ConfigurationException e) {
			fail("Configuration Failed");
		}
		
		Network network = model.generate();
		
		
		assertNotNull(network);
	
		
	}
	

}
