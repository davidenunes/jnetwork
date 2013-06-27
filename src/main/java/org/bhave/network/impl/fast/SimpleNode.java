/**
 * Copyright 2013 Davide Nunes Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * This file is part of the b-have network library.
 *
 * The b-have network library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The b-have network library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * the b-have network library. If not, see
 * <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.network.impl.fast;

import java.util.Properties;
import org.bhave.network.api.Network;

import org.bhave.network.api.Node;

/**
 * Implementation of {@link Node}
 *
 * @author Davide Nunes
 */
public class SimpleNode implements Node {

    private int id;
    private Properties properties;
    private Network network;

    public SimpleNode(int id) {
        this.id = id;
        this.properties = new Properties();
    }

    /**
     * Copy Constructor. Creates a link by copying an existing Link. This is to
     * avoid the clone
     *
     * @param node a node to be copied
     */
    public SimpleNode(SimpleNode node) {
        this(node.getID());

        for (Object key : node.properties.keySet()) {
            this.properties.put(key, node.properties.get(key));
        }

    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);

    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String toString() {
        return "SimpleNode [id=" + id + "]";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SimpleNode other = (SimpleNode) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * Returns a deep copy of the current node.
     *
     * @return a {@link Node} which is a copy of the current one.
     */
    @Override
    public Node getCopy() {
        return new SimpleNode(this);
    }

    /**
     * Returns the network with which this node is currently associated with.
     *
     * @return Network a reference to a {@link Network} object.
     */
    @Override
    public Network getNetwork() {
        return network;
    }

    @Override
    public void setNetwork(Network network) {
        this.network = network;
    }
}
