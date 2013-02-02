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
 * attachment. This interface defines a more specific configure method that
 * receives the expected model configuration parameters.
 * </p>
 * 
 * <h2>Configuring The Model</h2>
 * <p>
 * You can either use the method {@link BarabasiAlbertModel#configure(int, int)}
 * present in the current interface or the general
 * {@link NetworkModel#configure(org.apache.commons.configuration.Configuration)
 * configure} method from the {@link NetworkModel} interface.
 * </p>
 * 
 * <h3>Model Parameters</h3> <b>numNodes</b> - number of nodes to be added to
 * the network <br>
 * 
 * <b>d</b> - the minimum degree of each node added during the network growth<br>
 * 
 * <b>seed</b> - The seed to be used with the random number generator of this
 * network model </p>
 * 
 * 
 * 
 * 
 * @author Davide Nunes
 * 
 */
public interface BarabasiAlbertModel extends NetworkModel {

	/**
	 * Configures the model to be executed with a given number of nodes and a
	 * number of links to be added with each new node addition
	 * 
	 * @param numNodes
	 *            the number of nodes to be created in this model
	 * 
	 * @param d
	 *            minimum degree for each new node added. Dictates how many
	 *            links are added with preferential attachment, each iteration.
	 * 
	 * @param seed
	 *            the random number generator seed to be used with this
	 *            NetworkModel
	 */
	void configure(int numNodes, int d, long seed)
			throws ConfigurationException;

}
