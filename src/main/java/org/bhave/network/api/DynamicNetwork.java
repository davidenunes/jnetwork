/**
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * This file is part of the b-have network library.
 * 
 * The b-have network library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The b-have network library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the b-have network library.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.network.api;

import java.util.Set;

/**
 * 
 * <p>
 * Defines a network / graph data structure that contains <b>Nodes</b> with
 * connections between them called edges or <b>Links</b>. It has the same
 * interface as a {@link Network Network} with additional elements to allow to
 * model change over discrete time. What this means is that we can keep track of
 * nodes, links, node values and link values over time in the same network
 * structure.
 * </p>
 * 
 * <p>
 * {@link DynamicNetwork} instances are created with one time instance by
 * default. This is t = 0 or t = {@link DynamicNetwork#getFirstTime()} if you
 * prefer. You can use the network as you would use the {@link Network}
 * interface.
 * </p>
 * 
 * <p>
 * If we wish to create another instant in time for the network, we can just use
 * {@link DynamicNetwork#setCurrentTime(int)} with the desired value (for
 * example 1, 2, 50, ..., etc). Whenever
 * {@link DynamicNetwork#setCurrentTime(int)} is used with a given value for
 * <i>t</i>, if this time instance does not yet exist, it is initialised with a
 * copy of the network state from the time instance immediately before the given
 * <i>t</i>.
 * 
 * <br>
 * As an example, if your network contains the time instances {0, 1, 2}, and you
 * use {@link DynamicNetwork#setCurrentTime(int)} with t = 3, the new time
 * instance will contain a network with a deep copy of t = 2.
 * </p>
 * 
 * <b>Node:</b> time instants do not have to be contiguous. Just with a positive
 * value t >= 0. You can have time instances like {0, 1, 5, 6, 10, 11, 12}
 * 
 * <h2>Getting a Network Instance</h2>
 * <p>
 * Just like with {@link Network}, you can get dynamic network instances as
 * follows:
 * 
 * <br>
 * <br>
 * 
 * <code>Injector injector = Guice.createInjector(new NetworkModule());</code><br />
 * <code>Network network = injector.getInstance(Network.class);</code>
 * 
 * </p>
 * 
 * @author Davide Nunes
 * 
 */
public interface DynamicNetwork extends Network {

	/**
	 * Sets the current instance of this network to a given discrete time t.
	 * Does Nothing if t is negative.
	 * 
	 * @param t
	 *            a discrete time with t >= 0
	 */
	public void setCurrentTime(int t);

	/**
	 * Returns the current time instant in which you are working
	 * 
	 * @return t a discrete time
	 */
	public int getCurrentTime();

	/**
	 * Returns the last time instant of this network
	 * 
	 * 
	 * @return t a discrete time
	 */
	public int getLastTime();

	/**
	 * Returns the first time instant of this network
	 * 
	 * 
	 * @return t a discrete time
	 */
	public int getFirstTime();

	/**
	 * Returns the set of time instances for which there are events of node /
	 * link insertion, etc.
	 * 
	 * Example: {0, 1, 10, 11, 50}
	 * 
	 * <br>
	 * <br>
	 * 
	 * <b>Note: </b> when dynamic networks are created they contain a time
	 * instance set {0}
	 * 
	 * @return a set of time instances
	 */
	public Set<? extends Integer> getTimeInstances();
}
