package org.bhave.network.impl.hash.dynamic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.bhave.network.api.DynamicNetwork;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;

import com.google.inject.Inject;
import com.google.inject.Injector;

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

	private Injector injector = Guice.createInjector(new Module());
	private int currentTime;
	private Map<Integer, Network> networks; // map time intant t to a network

	
	
	public DynamicHashNetwork() {
		networks = new HashMap<>();
		currentTime = 0;
		
		//initial network (moment 0)
		@Inject Network network;
		
	}

	
	
	@Override
	public boolean addNode(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Link addLink(Node node1, Node node2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addLink(Node node1, Node node2, Link link) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeNode(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeNode(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeLink(Link link) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeLink(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node getNode(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link getLink(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link getLink(Node node1, Node node2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Link> getLinks(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Link> getOutLinks(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Link> getInLinks(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Node> getSuccessors(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Node> getPredecessors(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Node> getNeighbours(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Node> getNeighbours(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Node> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Link> getLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Node, Node> getNodes(int linkID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Node, Node> getNodes(Link link) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLinkCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean containsNode(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsLink(Link link) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node createNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link createLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTime(int t) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<? extends Integer> getTimeInstances() {
		// TODO Auto-generated method stub
		return null;
	}

}
