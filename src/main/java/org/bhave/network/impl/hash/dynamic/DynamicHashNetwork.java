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
package org.bhave.network.impl.hash.dynamic;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.Pair;
import org.bhave.network.api.DynamicNetwork;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.impl.fast.SimpleLink;
import org.bhave.network.impl.fast.SimpleNode;

import com.google.inject.Inject;
import org.bhave.network.impl.fast.FastNetwork;

/**
 * Implementation of {@link DynamicNetwork dynamic network}. This is a simple
 * implementation which wraps around multiple instances of {@link Network
 * Network} objects. Each of these instances represent a moment in time in which
 * nodes or links were added.
 *
 *
 *
 *
 * @author Davide Nunes
 *
 */
public class DynamicHashNetwork implements DynamicNetwork {

    private int currentTime;
    private NavigableMap<Integer, FastNetwork> networks; // map time instant to
    // a network

    /**
     * Constructor
     *
     * Starts with a dynamic network with an initial time instant of 0, this
     * represents an initial state of the network. If you don't use a
     *
     * @{link {@link DynamicNetwork#setCurrentTime(int) setTime} operation, this
     * network works exactly like a normal network would.
     */
    @Inject
    public DynamicHashNetwork() {
        networks = new TreeMap<>();
        currentTime = 0;

        // initial network (moment 0)
        FastNetwork initNetwork = new FastNetwork();
        networks.put(currentTime, initNetwork);

    }

    @Override
    public boolean addNode(Node node) {
        Network network = networks.get(currentTime);

        // create a safe copy

        // we assume nodes are created by HashNetwork
        // so we also use simple link
        Node nodeCopy = new SimpleNode((SimpleNode) node);

        return network.addNode(nodeCopy);
    }

    @Override
    public Link addLink(Node node1, Node node2) {
        Network network = networks.get(currentTime);

        return network.addLink(node1, node2);
    }

    @Override
    public boolean removeNode(Node node) {
        Network network = networks.get(currentTime);
        return network.removeNode(node);
    }

    @Override
    public boolean removeNode(int id) {
        Network network = networks.get(currentTime);
        return network.removeLink(id);
    }

    @Override
    public boolean removeLink(Link link) {
        Network network = networks.get(currentTime);
        return network.removeLink(link);
    }

    @Override
    public boolean removeLink(int id) {
        Network network = networks.get(currentTime);
        return network.removeLink(id);
    }

    @Override
    public Node getNode(int id) {
        Network network = networks.get(currentTime);
        return network.getNode(id);
    }

    @Override
    public Link getLink(int id) {
        Network network = networks.get(currentTime);
        return network.getLink(id);
    }

    @Override
    public Link getLink(Node node1, Node node2) {
        Network network = networks.get(currentTime);
        return network.getLink(node1, node2);
    }

    @Override
    public Collection<? extends Link> getLinks(Node node) {
        Network network = networks.get(currentTime);
        return network.getLinks(node);
    }

    @Override
    public Collection<? extends Link> getOutLinks(Node node) {
        Network network = networks.get(currentTime);
        return network.getOutLinks(node);
    }

    @Override
    public Collection<? extends Link> getInLinks(Node node) {
        Network network = networks.get(currentTime);
        return network.getInLinks(node);
    }

    @Override
    public Collection<? extends Node> getSuccessors(Node node) {
        Network network = networks.get(currentTime);
        return network.getSuccessors(node);
    }

    @Override
    public Collection<? extends Node> getPredecessors(Node node) {
        Network network = networks.get(currentTime);
        return network.getPredecessors(node);
    }

    @Override
    public Collection<? extends Node> getNeighbours(Node node) {
        Network network = networks.get(currentTime);
        return network.getNeighbours(node);
    }

    @Override
    public Collection<? extends Node> getNeighbours(int id) {
        Network network = networks.get(currentTime);
        return network.getNeighbours(id);
    }

    @Override
    public Collection<? extends Node> getNodes() {
        Network network = networks.get(currentTime);
        return network.getNodes();
    }

    @Override
    public Collection<? extends Link> getLinks() {
        Network network = networks.get(currentTime);
        return network.getLinks();
    }

    @Override
    public int getNodeCount() {
        return networks.get(currentTime).getNodeCount();
    }

    @Override
    public int getLinkCount() {
        return networks.get(currentTime).getNodeCount();
    }

    @Override
    public boolean containsNode(Node node) {
        return networks.get(currentTime).containsNode(node);
    }

    @Override
    public boolean containsLink(Link link) {
        return networks.get(currentTime).containsLink(link);
    }

    @Override
    public Node createNode() {
        return networks.get(currentTime).createNode();
    }

    @Override
    public Link createLink(Node from, Node to) {
        return networks.get(currentTime).createLink(from, to);
    }

    @Override
    public boolean containsLink(Node node1, Node node2) {
        return networks.get(currentTime).containsLink(node1, node2);
    }

    @Override
    public boolean containsDirectedLink(Node node1, Node node2) {
        return networks.get(currentTime).containsDirectedLink(node1, node2);
    }

    /**
     * Changes the current time instant of this dynamic network. What this means
     * is that if there is not a network structure associated
     */
    @Override
    public void setCurrentTime(int t) {
        if (t >= 0) {
            int previousTime = networks.floorKey(t);
            if (!networks.containsKey(t)) {
                FastNetwork newNetwork = new FastNetwork(
                        networks.get(previousTime));
                networks.put(t, newNetwork);
            }
            currentTime = t;
        }
    }

    @Override
    public int getCurrentTime() {
        return currentTime;
    }

    @Override
    public Set<? extends Integer> getTimeInstances() {
        return networks.keySet();
    }

    @Override
    public int getLastTime() {

        return networks.lastKey();
    }

    @Override
    public int getFirstTime() {

        return networks.firstKey();
    }
}
