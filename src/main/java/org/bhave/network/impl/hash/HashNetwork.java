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
package org.bhave.network.impl.hash;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.tuple.Pair;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;

import com.google.inject.Inject;

/**
 * Implementation of the {@link Network} interface. 
 * 
 * @author Davide Nunes
 * @see Network
 */
public class HashNetwork implements Network {
	private HashMap<Integer, Node> nodes; // the nodes in the network
	private HashMap<Integer, Link> links; // the links in the network

	private MultiKeyMap nl; // map the nodes to links
	private HashMap<Link, Pair<Node, Node>> ln; // map
												// links
												// to
												// nodes

	/**
	 * Constructor. Initialises all the sets of nodes, links and all the other
	 * necessary structures
	 * 
	 */
	@Inject
	public HashNetwork() {
		nl = new MultiKeyMap();
		ln = new HashMap<>();

		nodes = new HashMap<>();
		links = new HashMap<>();
	}

	@Override
	public boolean addNode(Node node) {
		if (node == null || containsNode(node))
			return false;
		nodes.put(node.getID(), node);
		return true;
	}

	@Override
	public SimpleLink addLink(Node node1, Node node2) {
		if (node1 == null || node2 == null
				|| !(containsNode(node1) && containsNode(node2)))
			return null;

		// create and add link object
		SimpleLink link = createLink();
		links.put(link.getID(), link);

		mapNodestoLinks(node1, node2, link);
		return link;
	}

	private void mapNodestoLinks(Node node1, Node node2, Link link) {
		// MultiKey Objects are order sensitive so this works if we want to
		// create directed networks
		MultiKey nodeKey = new MultiKey(node1, node2);

		// map nodes to links for efficient access
		nl.put(nodeKey, link);

		// map links to nodes to links for efficient access
		ln.put(link, Pair.of(node1, node2));

	}

	private void removeNodeToLinkMap(Node node1, Node node2, Link link) {
		MultiKey nodeKey = new MultiKey(node1, node2);
		nl.remove(nodeKey);

		ln.remove(link);
	}

	@Override
	public boolean addLink(Node node1, Node node2, Link link) {
		if (node1 != null && node2 != null && link != null
				&& containsNode(node1) && containsNode(node2)) {
			links.put(link.getID(), link);
			mapNodestoLinks(node1, node2, link);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeNode(Node node) {
		if (node == null || !containsNode(node))
			return false;

		for (Link link : getLinks(node)) {
			removeLink((SimpleLink) link);
		}
		nodes.remove(node.getID());
		return true;
	}

	@Override
	public boolean removeNode(int id) {
		SimpleNode dummyNode = new SimpleNode(id);
		return removeNode(dummyNode);
	}

	@Override
	public boolean removeLink(Link link) {
		if (link == null || !containsLink(link))
			return false;

		Pair<Node, Node> nodes = ln.get(link);
		removeNodeToLinkMap(nodes.getLeft(), nodes.getRight(), link);
		links.remove(link.getID());
		return true;
	}

	@Override
	public boolean removeLink(int id) {
		SimpleLink dummyLink = new SimpleLink(id);
		return removeLink(dummyLink);
	}

	@Override
	public Node getNode(int id) {
		return nodes.get(id);
	}

	@Override
	public Link getLink(int id) {
		return links.get(id);
	}

	@Override
	public SimpleLink getLink(Node node1, Node node2) {
		MultiKey nodeKey = new MultiKey(node1, node2);
		return (SimpleLink) nl.get(nodeKey);
	}

	/**
	 * Returns all the links from the nodes to link table where this contains a
	 * key with the given node at the given index 0 or 1
	 * 
	 * @return a set of links if these exist or an empty set if there are no
	 *         links
	 * 
	 */
	private Collection<? extends Link> getLinks(Node node, int keyIndex) {
		if (node == null || !containsNode(node))
			return null;

		HashSet<Link> result = new HashSet<Link>();
		if (containsNode(node)) {

			for (Object key : nl.keySet()) {
				MultiKey nodeKey = (MultiKey) key;

				if (nodeKey.getKey(keyIndex).equals(node)) {// out link found
					result.add((SimpleLink) nl.get(nodeKey));
				}
			}

		}
		return result;
	}

	@Override
	public Collection<? extends Link> getOutLinks(Node node) {
		return getLinks(node, 0);// a key with the node at the index 0 is a out
									// link
	}

	@Override
	public Collection<? extends Link> getInLinks(Node node) {
		return getLinks(node, 1); // in link
	}

	@Override
	public Collection<? extends Link> getLinks(Node node) {
		if (node == null || !containsNode(node))
			return null;

		HashSet<Link> allLinks = new HashSet<Link>();
		allLinks.addAll(getInLinks(node));
		allLinks.addAll(getOutLinks(node));
		return allLinks;
	}

	@Override
	public Collection<? extends Node> getNeighbours(Node node) {
		if (node == null || !containsNode(node))
			return null;

		// all the neighbour were these nodes have in or out links
		Collection<? extends Link> allLinks = getLinks(node);

		HashSet<Node> result = new HashSet<Node>();
		for (Link link : allLinks) {
			result.add(returnOther(node, ln.get(link)));
		}

		return result;
	}

	/**
	 * Return the node in the pair that differs from the given node
	 * 
	 * @return a node
	 */
	private Node returnOther(Node node, Pair<Node, Node> nodes) {
		if (nodes.getLeft().equals(node)) {
			return nodes.getRight();
		}// else
		return nodes.getLeft();
	}

	@Override
	public Collection<? extends Node> getNeighbours(int id) {
		SimpleNode dummyNode = new SimpleNode(id);
		return getNeighbours(dummyNode);
	}

	@Override
	public Pair<Node, Node> getNodes(int linkID) {
		Link link = links.get(linkID);
		if (link == null)
			return null;
		return ln.get(link);
	}

	@Override
	public Pair<Node, Node> getNodes(Link link) {
		if (link == null || !containsLink(link))
			return null;
		return getNodes(link.getID());
	}

	@Override
	public int getNodeCount() {
		return nodes.size();
	}

	@Override
	public int getLinkCount() {
		return links.size();
	}

	@Override
	public boolean containsNode(Node node) {
		return node == null ? false : nodes.containsKey(node.getID());
	}

	@Override
	public boolean containsLink(Link link) {
		return link == null ? false : links.containsKey(link.getID());
	}

	@Override
	public Collection<? extends Node> getSuccessors(Node node) {
		if (node == null || !containsNode(node))
			return null;
		HashSet<Node> result = new HashSet<>();
		for (Link link : getOutLinks(node))
			result.add(returnOther(node, ln.get(link)));

		return result;
	}

	@Override
	public Collection<? extends Node> getPredecessors(Node node) {
		if (node == null || !containsNode(node))
			return null;
		HashSet<Node> result = new HashSet<>();
		for (Link link : getInLinks(node))
			result.add(returnOther(node, ln.get(link)));

		return result;
	}

	private int nextNodeID = 0;
	private int nextLinkID = 0;

	@Override
	public SimpleNode createNode() {
		return new SimpleNode(nextNodeID++);
	}

	@Override
	public SimpleLink createLink() {
		return new SimpleLink(nextLinkID++);
	}

	@Override
	public Collection<? extends Node> getNodes() {
		HashSet<Node> result = new HashSet<Node>();
		for (Integer key : nodes.keySet())
			result.add(nodes.get(key));

		return result;
	}

	@Override
	public Collection<? extends Link> getLinks() {
		HashSet<Link> result = new HashSet<Link>();
		for (Integer key : links.keySet())
			result.add(links.get(key));

		return result;
	}

}
