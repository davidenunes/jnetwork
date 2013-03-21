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

/**
 * Defines a <b>Node</b> entity. These are created by network instances and
 * serve as a placeholder for various properties. This is the equivalent to a
 * Graph Vertex for instance. They should contain unique ids within the scope of
 * the network that created them.
 * 
 * @see Network
 * @see Network#createNode()
 * @author Davide Nunes
 */
public interface Node {

	/**
	 * Returns the Integer ID of this Node. This is the component used to
	 * compare nodes in a network.
	 * 
	 * @return an integer id
	 */
	public int getID();

	/**
	 * Adds or sets a property to this node. The given key should be the
	 * property name. The given value should be its value.
	 * 
	 * @param key
	 *            a String representation of the name of the property
	 * @param value
	 *            a String representation of the property value
	 */
	public void setProperty(String key, String value);

	/**
	 * Returns the value of the property with the name given by the given key,
	 * or null if no property with this name exists.
	 * 
	 * @param key
	 *            a property name
	 * 
	 * @return the property value.
	 */
	public String getProperty(String key);

	/**
	 * Returns a deep copy of this node
	 * 
	 * @return a node copy
	 */
	public Node getCopy();

	/**
	 * Returns the network this node belongs to
	 * 
	 * @return a network
	 */
	public Network getNetwork();

	/**
	 * sets the network this node belongs to
	 * 
	 * @param network
	 */
	public void setNetwork(Network network);

}
