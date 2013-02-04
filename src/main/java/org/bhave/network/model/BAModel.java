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
 * Defines an interface for the BarabasiAbert Network Model of preferential
 * attachment.
 * </p>
 * 
 * <h2>Configuring The Model</h2>
 * <p>
 * You can either use the method {@link BAModel#configure(int, int, long)}
 * present in the current interface or the general
 * {@link NetworkModel#configure(org.apache.commons.configuration.Configuration)
 * configure} method from the {@link NetworkModel} interface.
 * </p>
 * 
 * <h3>Model Parameters</h3> <b>numNodes</b> - number of nodes to be added to
 * the network <br>
 * 
 * <b>d</b> - number of links to be added to the network each time a node is
 * added. <br>
 * 
 * <b>seed</b> - The seed to be used with the random number generator of this
 * network model </p>
 * 
 * <h2>Working with a model instance</h2> <code>
 * Injector injector = Guice.createInjector(new NetworkModule());<br>
 * BAModel model = injector.getInstance(BAModel.class);<br><br>
 * 
 * Configuration config = model.getConfiguration();<br>
 * config.setProperty("numNodes", 1000);<br>
 * config.setProperty("numLinks", 1);<br>
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
public interface BAModel extends NetworkModel {

	/**
	 * Configures the model to be executed with a given number of nodes and a
	 * number of links to be added with each new node addition
	 * 
	 * @param numNodes
	 *            the number of nodes
	 * 
	 * @param d
	 *            number of links created each iteration of the network growth
	 * 
	 * @param seed
	 *            the random number generator seed
	 */
	void configure(int numNodes, int d, long seed)
			throws ConfigurationException;

}
