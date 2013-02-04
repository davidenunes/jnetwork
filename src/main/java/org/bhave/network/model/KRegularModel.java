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
package org.bhave.network.model;

import org.apache.commons.configuration.ConfigurationException;

/**
 * <p>
 * Defines an interface for the KRegular Network Model. This model generates
 * k-regular ring lattice by connecting each node to their next k neighbours.
 * Each node thus yields the same degree (2k).
 * </p>
 * 
 * <h2>Configuring The Model</h2>
 * <p>
 * You can either use the method {@link KRegularModel#configure(int, int, long)}
 * present in the current interface or the general
 * {@link NetworkModel#configure(org.apache.commons.configuration.Configuration)
 * configure} method from the {@link NetworkModel} interface.
 * </p>
 * 
 * <h3>Model Parameters</h3> <b>numNodes</b> - number of nodes to be added to
 * the network <br>
 * 
 * <b>k</b> - each node is attached to their k neighbours in a ring. Each node
 * will have a degree of 2K.<br>
 * 
 * <b>seed</b> - The seed to be used with the random number generator of this
 * network model </p>
 * 
 * <h2>Working with a model instance</h2> <code>
 * Injector injector = Guice.createInjector(new NetworkModule());<br>
 * KRegularModel model = injector.getInstance(KRegularModel.class);<br><br>
 * 
 * Configuration config = model.getConfiguration();<br>
 * config.setProperty("numNodes", 1000);<br>
 * config.setProperty("k", 1);<br>
 * config.setProperty("seed", 0);<br><br>
 * 
 * try{<br>
 * 		model.configure(config);<br>
 * }catch(ConfigurationException e){}<br><br>
 * Network network = model.generate();<br>
 * </code> <br>
 * 
 * 
 * 
 * @author Davide Nunes
 * 
 */
public interface KRegularModel extends NetworkModel {

	/**
	 * Configures the model. The resulting network will have a given number of
	 * nodes with 2K degree and it is constructed with a random number generator
	 * using the provided seed.
	 * 
	 * @param numNodes
	 *            the number of nodes to be created
	 * 
	 * @param k
	 *            each node is attached to their k neighbours in a ring. Each
	 *            node will have a degree of 2K.
	 * 
	 * @param seed
	 *            the random number generator seed
	 */
	void configure(int numNodes, int k, long seed) throws ConfigurationException;
}
