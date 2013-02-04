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
 * /**
 * <p>
 * Defines an interface for the BarabasiAbert Network Model of preferential
 * attachment. This model is an efficient version of {@link BAModel} using only
 * the parameter d = 1. This means that each time a node is added to the
 * network, only one link is created with preferential attachment. This process
 * thus creates a connected forest graph / network. A forest is basically an
 * acyclic graph.
 * </p>
 * 
 * <h2>Configuring The Model</h2>
 * <p>
 * You can either use the method {@link BAModel#configure(int, long)} present in
 * the current interface or the general
 * {@link NetworkModel#configure(org.apache.commons.configuration.Configuration)
 * configure} method from the {@link NetworkModel} interface.
 * </p>
 * 
 * <h3>Model Parameters</h3> <b>numNodes</b> - number of nodes to be added to
 * the network <br>
 * 
 * <b>seed</b> - The seed to be used with the random number generator of this
 * network model </p>
 * 
 * <h2>Working with a model instance</h2> <code>
 * Injector injector = Guice.createInjector(new NetworkModule());<br>
 * BAForestModel model = injector.getInstance(BAForestModel.class);<br><br>
 * 
 * Configuration config = model.getConfiguration();<br>
 * config.setProperty("numNodes", 1000);<br>
 * config.setProperty("seed", 0);<br><br>
 * 
 * try{<br>
 * 		model.configure(config);<br>
 * }catch(ConfigurationException e){}<br><br>
 * Network network = model.generate();<br>
 * </code> <br>
 * 
 * 
 * @author Davide Nunes
 * 
 */
public interface BAForestModel extends NetworkModel {
	/**
	 * Configures the model to be executed with a given number of nodes and a
	 * seed for the random number generator.
	 * 
	 * @param numNodes
	 *            the number of nodes to be created
	 * 
	 * 
	 * @param seed
	 *            the random number generator seed
	 */
	void configure(int numNodes, long seed) throws ConfigurationException;

}
