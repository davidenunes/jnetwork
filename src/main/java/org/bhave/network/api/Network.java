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

import java.util.Collection;

import org.apache.commons.lang3.tuple.Pair;
import org.bhave.network.NetworkModule;

/**
 * <p>
 * Defines a network / graph data structure that contains <b>Nodes</b> with
 * connections between them called edges or <b>Links</b>. This is just the
 * interface to access and manipulate network structures provided by existing
 * implementations.
 * </p>
 * 
 * <p>
 * <code>Node</code> and <code>Link</code> objects should be created using the
 * respective network instance using {@link #createNode() createNode} and
 * {@link #createLink() createLink} methods. This avoids dealing with specific
 * Node or Link implementations.
 * 
 * <br />
 * <br />
 * <b>Note:</b> by creating a new node or link you are not adding them to the
 * network. You still have to use {@link #addNode(Node) addNode} or
 * {@link #addLink(Node, Node, Link) addLink}.
 * </p>
 * <br />
 * <br />
 * 
 * <h2>Getting a Network Instance</h2>
 * <p>
 * This library uses <a href="http://code.google.com/p/google-guice/">Guice</a>
 * to deal with network instance creation, hence, rather than worrying about
 * specific implementations, a user can just request <code>Network</code>
 * instances from <b>Guice</b> as follows: <br />
 * <br />
 * 
 * <code>Injector injector = Guice.createInjector(new NetworkModule());</code><br />
 * <code>Network network = injector.getInstance(Network.class);</code>
 * 
 * <br />
 * <br />
 * </p>
 * 
 * @see Node
 * @see Link
 * @see NetworkModule
 * 
 * @author Davide Nunes
 */
public interface Network{

	/**
	 * Adds a node to the network. Fails if the node already exists or if the
	 * node is null.
	 * 
	 * @param node
	 *            the node to be added
	 * 
	 * @return true if add is successful, false otherwise.
	 */
	boolean addNode(Node node);

	/**
	 * Adds a Link to the graph. Fails if the Link is already in the Network, if
	 * the nodes do not exist in the network or if any of these elements is
	 * null. <br />
	 * <br />
	 * <b>Note:</b> the network is both <b>directed</b> and <b>undirected</b> by
	 * default. What this means is that if you wish to work with a directed
	 * network, the order of the given nodes dictates the direction of the link.
	 * If you wish to work with an undirected network, the order is not
	 * important, just use the methods that do not take link direction in
	 * consideration.
	 * 
	 * @param node1
	 *            an existing node within the network
	 * @param node2
	 *            an existing node within the network
	 * 
	 * @return link a link if add is successful, null otherwise
	 */
	Link addLink(Node node1, Node node2);

	/**
	 * Creates and adds a link to the network between the given nodes. Fails if
	 * the nodes do not exist in the network or if any of them is null. <br />
	 * <br />
	 * <b>Note:</b> the network is both <b>directed</b> and <b>undirected</b> by
	 * default. What this means is that if you wish to work with a directed
	 * network, the order of the given nodes dictates the direction of the link.
	 * If you wish to work with an undirected network, the order is not
	 * important, just use the methods that do not take link direction in
	 * consideration.
	 * 
	 * @param node1
	 *            an existing node within the network
	 * @param node2
	 *            an existing node within the network
	 * @param link
	 *            a link to be added from node1 to node2
	 * 
	 * @return true if add is successful, false otherwise
	 */
	boolean addLink(Node node1, Node node2, Link link);

	/**
	 * Removes a node from the network. Fails if the node does not exist or if
	 * the node is null. <br />
	 * <br />
	 * <b>Note:</b>removing a node with existing links, also removes these
	 * links.
	 * 
	 * @param node
	 *            the node to be removed
	 * 
	 * @return true if remove is successful, false otherwise
	 */
	boolean removeNode(Node node);

	/**
	 * Removes a node from the network based on its ID. Fails if the node does
	 * not exist.
	 * 
	 * <b>Note:</b>removing a node with existing links, also removes these
	 * links.
	 * 
	 * @param id
	 *            the id of the node to be removed
	 * 
	 * @return true if remove is successful, false otherwise
	 */
	boolean removeNode(int id);

	/**
	 * Removes a link from the network. Fails if the link does not exist of if
	 * the link is null.
	 * 
	 * @param link
	 *            the link to be removed
	 * 
	 * @return true if remove is successful, false otherwise
	 */
	boolean removeLink(Link link);

	/**
	 * Removes a link from the network based on its id. Fails if the link does
	 * not exist.
	 * 
	 * @param id
	 *            the id of the link to be removed
	 * 
	 * @return true if remove is successful, false otherwise
	 */
	boolean removeLink(int id);

	/**
	 * Returns a node by its Integer id or null if the node does not exist.
	 * 
	 * @param id
	 *            the id of the node to be returned
	 * 
	 * @return an existing node, null if the node does not exist.
	 */
	Node getNode(int id);

	/**
	 * Returns a link by its Integer id or null if the node does not exist.
	 * 
	 * @param id
	 *            the id of the link to be returned
	 * 
	 * @return an existing link, null if the link does not exist.
	 */
	Link getLink(int id);

	/**
	 * Returns a link given the two nodes it connects.
	 * 
	 * @param node1
	 *            a node attached to the start of the link
	 * @param node2
	 *            a node attached to the end of the link
	 * 
	 * @return an existing link, null if the link does not exist or any given
	 *         nodes are null.
	 */
	Link getLink(Node node1, Node node2);

	/**
	 * Returns a collection of links attached to the given node.
	 * 
	 * @param node
	 *            the node from which we wish to extract the links from.
	 * 
	 * @return a collection of links or null if this node does not exist in the
	 *         network or is null.
	 */
	Collection<? extends Link> getLinks(Node node);

	/**
	 * Returns a collection of links coming <b>from</b> the given node to other
	 * nodes. You should use this method if you are using the network as a
	 * directed network.
	 * 
	 * @param node
	 *            the node from which we wish to extract the links from.
	 * 
	 * @return a collection of links or null if this node does not exist in the
	 *         network or is null.
	 */
	Collection<? extends Link> getOutLinks(Node node);

	/**
	 * Returns a collection of links coming <b>to</b> the given node from other
	 * nodes. You should use this method if you are using the network as a
	 * directed network.
	 * 
	 * @param node
	 *            the node from which we wish to extract the links from.
	 * 
	 * @return a collection of links or null if this node does not exist in the
	 *         network or is null.
	 */
	Collection<? extends Link> getInLinks(Node node);

	/**
	 * Returns a collection of nodes attached to link coming from the given
	 * node.
	 * 
	 * @param node
	 *            the current node we want the successors from
	 * @return a collection of nodes or null if the given node does not exist
	 *         within the network or is null
	 */
	Collection<? extends Node> getSuccessors(Node node);

	/**
	 * Returns a collection of nodes attached to links coming to the given node.
	 * 
	 * @param node
	 *            the current node we want the predecessors from
	 * @return a collection of nodes or null if the given node does not exist
	 *         within the network or is null
	 */
	Collection<? extends Node> getPredecessors(Node node);

	/**
	 * Returns a collection of nodes attached to the given node by some link.
	 * 
	 * @param node
	 *            the node we want the neighbours from.
	 * 
	 * @return a collection of nodes or null if the given node does not exist
	 *         within the network or is null
	 */
	Collection<? extends Node> getNeighbours(Node node);

	/**
	 * Returns a collection of nodes attached to the node with the given id by
	 * some link.
	 * 
	 * @param id
	 *            the id of the node we want the neighbours from.
	 * 
	 * @return a collection of nodes or null if the given node does not exist
	 *         within the network or is null
	 */
	Collection<? extends Node> getNeighbours(int id);

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
	 * Returns the two nodes attached to a link with the given id.
	 * 
	 * @param linkID
	 *            the id of the link from which we want to retrieve the nodes.
	 * 
	 * @return a pair of nodes or null if the link does not exist in the
	 *         network.
	 * @see Pair (from <a href=
	 *      "http://commons.apache.org/lang/api/org/apache/commons/lang3/tuple/Pair.html"
	 *      > org.apache.commons.lang3.tuple.Pair<L,R> </a>)
	 */
	Pair<Node, Node> getNodes(int linkID);

	/**
	 * Returns the two nodes that the given link connects.
	 * 
	 * @param link
	 *            the link from which we want to extract the nodes.
	 * @return a pair of nodes or null if the link is null or does not exist in
	 *         the network
	 * 
	 * @see Pair (from <a href=
	 *      "http://commons.apache.org/lang/api/org/apache/commons/lang3/tuple/Pair.html"
	 *      > org.apache.commons.lang3.tuple.Pair<L,R> </a>)
	 */
	Pair<Node, Node> getNodes(Link link);

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
	 * @param node
	 *            a node we want to check
	 * 
	 * @return true if the node is in the network, false if the node is not in
	 *         the network or if it is null.
	 */
	boolean containsNode(Node node);

	/**
	 * Returns true of the given link is in the network. False otherwise or if
	 * the link is null.
	 * 
	 * @param link
	 *            a link we want to check
	 * 
	 * @return true if the link is in the network, false if the link is not in
	 *         the network or if it is null.
	 */
	boolean containsLink(Link link);

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
	Link createLink();
	
}