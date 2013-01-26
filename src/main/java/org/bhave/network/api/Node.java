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

	public int getID();

	public void setProperty(String key, String value);

	public String getProperty(String key);

}
