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
 * <p>
 * Defines a network <b>Link</b>. These are used to connect network. In this API
 * however this is not done directly in the link instances. The network
 * instances handle the relationship between nodes and links. This is the
 * equivalent to a Graph <b>Edge</b> or <b>Arc</b> if you prefer.
 * </p>
 * 
 * <p>
 * Like the {@link Node nodes}, link objects should be created by their
 * corresponding {@link Network network} instances and provide facilities to set
 * and get various property values. For convenience, it also provides a set and
 * a get for a {@link Double double} value, normally associated with some kind
 * of distance between the nodes.
 * </p>
 * 
 * @see Node
 * @see Network
 * @see Network#createLink()
 * 
 * @author Davide Nunes
 */
public interface Link {

	/**
	 * Returns the Integer ID of this Link. This is the component used to
	 * compare links in a network.
	 * 
	 * @return an integer id
	 */
	public int getID();

	/**
	 * Returns a double value associated with the link. This is here for
	 * convenience. Typically this represents a distances in a networks for
	 * example.
	 * 
	 * @return value a double value associated with a distance between nodes
	 */
	public double getValue();

	/**
	 * Sets the value for this links "distance" value. This value can latter be
	 * accessed through {@link Link#getValue()}.
	 * 
	 * @param value
	 *            a value to be set for this link.
	 */
	public void setValue(double value);

	/**
	 * Adds or sets a property to this link. The given key should be the
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

}
