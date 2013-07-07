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
package org.bhave.network.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.bhave.network.NetworkModule;

/**
 * <p> Defines a network / graph data structure that contains <b>Nodes</b> with
 * connections between them called edges or <b>Links</b>. This is just the
 * interface to access and manipulate network structures provided by existing
 * implementations. </p>
 *
 * <p>
 * <code>Node</code> and
 * <code>Link</code> objects should be created using the respective network
 * instance using {@link #createNode() createNode} and
 * {@link #createLink() createLink} methods. This avoids dealing with specific
 * Node or Link implementations.
 *
 * <br /> <br /> <b>Note:</b> by creating a new node or link you are not adding
 * them to the network. You still have to use {@link #addNode(Node) addNode} or
 * {@link #addLink(Link) addLink} or {@link #addLink(Link) addLink} . </p> <br
 * /> <br />
 *
 * <h2>Getting a Network Instance</h2> <p> This library uses <a
 * href="http://code.google.com/p/google-guice/">Guice</a> to deal with network
 * instance creation, hence, rather than worrying about specific
 * implementations, a user can just request
 * <code>Network</code> instances from <b>Guice</b> as follows: <br /> <br />
 *
 * <code>Injector injector = Guice.createInjector(new NetworkModule());</code><br
 * />
 * <code>Network network = injector.getInstance(Network.class);</code> <p> The
 * <b>default implementation</b> for a network provides an <b>undirected
 * network</b>. If you specifically require a directed or undirected network you
 * can use the injector to get these as follows: <br>
 *
 * <code>Network network = injector.getInstance(DirectedNetwork.class);</code><br/>
 * <code>Network network = injector.getInstance(UndirectedNetwork.class);</code><br/>
 *
 * <br/> You can encapsulate the instances in {@link Network} objects or
 * directly use {@link DirectedNetwork} or {@link UndirectedNetwork} if this
 * helps with the code readability.
 *
 * </p>
 *
 * <br /> <br /> </p>
 *
 * @see Node
 * @see Link
 * @see NetworkModule
 *
 * @author Davide Nunes
 */
public interface Network extends Serializable {

    /**
     * Returns true if the network is directed
     *
     * @return true or false if the network is directed or not
     */
    boolean isDirected();

    /**
     * Adds a node to the network. Returns false if the node already exists or
     * if the node is null.
     *
     * @param node the node to be added
     *
     * @return true if add is successful, false otherwise.
     */
    boolean addNode(Node node);

    /**
     * Adds a Link to the graph. Fails if the Link is already in the Network, or
     * if any of these elements is null. <br /> <br />
     *
     * @param node1 an existing node within the network
     * @param node2 an existing node within the network
     *
     * @return link a link if add is successful, null otherwise
     */
    Link addLink(Node node1, Node node2);

    /**
     * Adds a Link to the graph. Fails if the Link is already in the Network, or
     * if any of these elements is null. <br /> <br />
     *
     * @param link a link to be added to the network, should be created with
     * network.createLink(node1,node2)
     *
     * If the nodes in the link do not exist in the network, these are added
     * automatically.
     *
     * @return link a link if add is successful, null otherwise
     */
    boolean addLink(Link link);

    /**
     * Removes a node from the network. Fails if the node does not exist or if
     * the node is null. <br /> <br /> <b>Note:</b>removing a node with existing
     * links, also removes these links.
     *
     * @param node the node to be removed
     *
     * @return true if remove is successful, false otherwise
     */
    boolean removeNode(Node node);

    /**
     * Removes a link from the network. Fails if the link does not exist of if
     * the link is null.
     *
     * @param link the link to be removed
     *
     * @return true if remove is successful, false otherwise
     */
    boolean removeLink(Link link);

    /**
     * Returns a node by its Integer id or null if the node does not exist.
     *
     * @param id the id of the node to be returned
     *
     * @return an existing node, null if the node does not exist.
     */
    Node getNode(int id);

    /**
     * Returns a link by its Integer id or null if the node does not exist.
     *
     * @param id the id of the link to be returned
     *
     * @return an existing link, null if the link does not exist.
     */
    Link getLink(int id);

    /**
     * Returns a link given the two nodes it connects.
     *
     * @param node1 a node attached to the start of the link
     * @param node2 a node attached to the end of the link
     *
     * @return an existing link, null if the link does not exist or any given
     * nodes are null.
     */
    Collection<? extends Link> getLinks(Node node1, Node node2);

    /**
     * Returns a collection of links attached to the given node.
     *
     * @param node the node from which we wish to extract the links from.
     *
     * @return a collection of links or null if this node does not exist in the
     * network or is null.
     */
    Collection<? extends Link> getLinks(Node node);

    /**
     * Returns a collection of links coming <b>from</b> the given node to other
     * nodes. You should use this method if you are using the network as a
     * directed network.
     *
     * @param node the node from which we wish to extract the links from.
     *
     * @return a collection of links or null if this node does not exist in the
     * network or is null.
     */
    Collection<? extends Link> getOutLinks(Node node);

    /**
     * Returns a collection of links coming <b>to</b> the given node from other
     * nodes. You should use this method if you are using the network as a
     * directed network.
     *
     * @param node the node from which we wish to extract the links from.
     *
     * @return a collection of links or null if this node does not exist in the
     * network or is null.
     */
    Collection<? extends Link> getInLinks(Node node);

    /**
     * Returns a collection of nodes attached to link coming from the given
     * node.
     *
     * @param node the current node we want the successors from
     * @return a collection of nodes or null if the given node does not exist
     * within the network or is null
     */
    Collection<? extends Node> getSuccessors(Node node);

    /**
     * Returns a collection of nodes attached to links coming to the given node.
     *
     * @param node the current node we want the predecessors from
     * @return a collection of nodes or null if the given node does not exist
     * within the network or is null
     */
    Collection<? extends Node> getPredecessors(Node node);

    /**
     * Returns a collection of nodes attached to the given node by some link.
     *
     * @param node the node we want the neighbours from.
     *
     * @return a collection of nodes or null if the given node does not exist
     * within the network or is null
     */
    Collection<? extends Node> getNeighbours(Node node);

    /**
     * Returns a collection of all the nodes in the network.
     *
     * @return a collection of nodes
     */
    Collection<? extends Node> getNodes();

    /**
     * Returns a collection of all the links in the network.
     *
     * @return a collection of links
     */
    Collection<? extends Link> getLinks();

    /**
     * Returns the number of nodes in the network.
     *
     * @return number of nodes in the network.
     */
    int getNodeCount();

    /**
     * Returns the number of links in the network.
     *
     * @return number of links in the network.
     */
    int getLinkCount();

    /**
     * Returns true of the given node is in the network. False otherwise or if
     * the node is null.
     *
     * @param node a node we want to check
     *
     * @return true if the node is in the network, false if the node is not in
     * the network or if it is null.
     */
    boolean containsNode(Node node);

    /**
     * Returns true of the given link is in the network. False otherwise or if
     * the link is null.
     *
     * @param link a link we want to check
     *
     * @return true if the link is in the network, false if the link is not in
     * the network or if it is null.
     */
    boolean containsLink(Link link);

    /**
     * Returns true of the given link is in the network. False otherwise or if
     * the link is null.
     *
     * @param node1 one of the nodes for the link
     * @param node2 one of the nodes for the link
     *
     * @return true if the link is in the network, false if the link is not in
     * the network or if it is null.
     */
    boolean containsLinks(Node node1, Node node2);

    /**
     * Creates a new node that can be added to the network.
     *
     * @return a node
     * @see Node
     */
    Node createNode();

    /**
     * Creates a new link that can be added to the network.
     *
     * @return a link
     * @see Link
     */
    Link createLink(Node from, Node to);

    /**
     * Creates a new Network instance which is a deep copy of the current
     * network object.
     *
     * @return a network
     */
    Network getCopy();
}