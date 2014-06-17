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
 * This model produces networks according to the Erdos-Renyi model as follows:
 * 
 * Each network is chosen uniformly at random from the collection of all
 * networks which have <b>n</b> nodes and <b>m</b> edges. This model generates
 * undirected networks without loops.
 * 
 * </p>
 * 
 * <h2>Configuring The Model</h2>
 * <p>
 * You can either use the method {@link ERModel#configure(int, int, long)}
 * present in the current interface or the general
 * {@link NetworkModel#configure(org.apache.commons.configuration.Configuration)
 * configure} method from the {@link NetworkModel} interface.
 * </p>
 * 
 * <h3>Model Parameters</h3> <b>numNodes</b> - number of nodes to be added to
 * the network <br>
 * 
 * <b>numLinks</b> - number of edges in the network<br>
 * 
 * <b>seed</b> - The seed to be used with the random number generator of this
 * network model </p>
 * 
 * @author Davide Nunes
 * 
 */
public interface ERModel extends NetworkModel {
        public static final String P_NUM_NODES = "numNodes";
        public static final String P_NUM_LINKS = "numLinks";
    
    
	/**
	 * Configures a model to produce a network with a given number of nodes and
	 * links.
	 * 
	 * @param numNodes
	 *            number of nodes in the network
	 * @param numLinks
	 *            number of links in the network
	 * 
	 * @param seed
	 *            the seed to be used by the random number generator
	 * @throws ConfigurationException
	 */
	void configure(int numNodes, int numLinks, long seed)
			throws ConfigurationException;

}
