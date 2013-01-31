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
import org.bhave.network.impl.hash.HashNetwork;
import org.bhave.network.impl.hash.SimpleLink;
import org.bhave.network.impl.hash.SimpleNode;

import com.google.inject.Inject;

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
	private NavigableMap<Integer, HashNetwork> networks; // map time instant to
															// a network

	/**
	 * Constructor
	 * 
	 * Starts with a dynamic network with an initial time instant of 0, this
	 * represents an initial state of the network. If you don't use a @{link
	 * {@link DynamicNetwork#setCurrentTime(int) setTime} operation, this network works
	 * exactly like a normal network would.
	 */
	@Inject
	public DynamicHashNetwork() {
		networks = new TreeMap<>();
		currentTime = 0;

		// initial network (moment 0)
		HashNetwork initNetwork = new HashNetwork();
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
	public boolean addLink(Node node1, Node node2, Link link) {
		Network network = networks.get(currentTime);

		// create a safe copy

		// we assume nodes are created by this class so we also use simple link
		Link linkCopy = new SimpleLink((SimpleLink) link);

		return network.addLink(node1, node2, linkCopy);
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
	public Pair<Node, Node> getNodes(int linkID) {
		Network network = networks.get(currentTime);
		return network.getNodes(linkID);
	}

	@Override
	public Pair<Node, Node> getNodes(Link link) {
		Network network = networks.get(currentTime);
		return network.getNodes(link);
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
	public Link createLink() {
		return networks.get(currentTime).createLink();
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
				HashNetwork newNetwork = new HashNetwork(
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
