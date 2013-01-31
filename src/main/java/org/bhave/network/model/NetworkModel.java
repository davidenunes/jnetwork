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

import java.util.Properties;

import org.bhave.network.api.Network;

/**
 * Defines an interface for Network models.
 * 
 * <p>
 * A network model can be seen as a {@link Network Network} factory. Instances
 * of this interface should be able to create network instances based on
 * algorithms for constructing network topologies with a target set of
 * topological features.
 * </p>
 * 
 * <p>
 * Among such generative procedures, we can include simple or <b>complex network
 * models</b>. Complex network models define ways to construct networks with
 * non-trivial topological features that do not occur in simple networks such as
 * lattices or random graphs but often occur in real graphs such as Online
 * Social networks. </b>
 * 
 * <h2>Network Model Configuration</h2>
 * <p>A <code>NetworkModel</code> should provide a way to configure the network 
 * generative procedure using the method {@link NetworkModel#configure(Properties)}.
 * The specific details of each configuration should be 
 * 
 * 
 * 
 * @author Davide Nunes
 * 
 */
public interface NetworkModel {
	
	public void configure(Properties parameters);

	public Network generate();
}
