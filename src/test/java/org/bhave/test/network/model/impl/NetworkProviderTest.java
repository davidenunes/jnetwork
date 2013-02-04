package org.bhave.test.network.model.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.bhave.network.NetworkModule;
import org.bhave.network.api.Network;
import org.bhave.network.model.KRegularModel;
import org.bhave.network.model.NetworkModel;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Thats that multiple calls on a network model generate do not yield the same
 * network
 * 
 * @author davide
 * 
 */
public class NetworkProviderTest {

	private static final Injector injector = Guice
			.createInjector(new NetworkModule());

	@Test
	public void test() {

		NetworkModel model = injector.getInstance(KRegularModel.class);
		assertNotNull(model);
		Network network1 = model.generate();

		Network network2 = model.generate();

		assertNotSame(network1, network2);

	}

}
