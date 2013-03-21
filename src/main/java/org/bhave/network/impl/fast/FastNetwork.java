package org.bhave.network.impl.fast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.tuple.Pair;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.impl.hash.SimpleLink;
import org.bhave.network.impl.hash.SimpleNode;

/**
 * Fast Implementation of {@link Network} interface.
 * 
 * @author Davide Nunes
 * 
 */
public class FastNetwork implements Serializable {
	private static final long serialVersionUID = 1L;

	boolean directed;
	// map node to index and links
	HashMap<Node, NodeIndex> nodeI;

	// store nodes
	ArrayList<Node> nodes;

	// store links
	ArrayList<Link> links;

	// map link to index in link
	HashMap<Link, LinkIndex> linkI;

	// directed map the nodes to links
	MultiKeyMap nodesToLinks;

	// constructors
	public FastNetwork() {
		this(false);
	}

	public FastNetwork(boolean directed) {
		this.directed = directed;
	}

	public FastNetwork(FastNetwork other) {
		this();
		other.copyTo(this);
		this.directed = other.directed;
	}

	/**
	 * Returns a deep copy of this network
	 * 
	 * @return a copy of this network
	 */
	public FastNetwork getCopy() {
		FastNetwork network = new FastNetwork(this);
		return network;
	}

	/**
	 * Adds a link to the network. If the nodes are not yet present in the
	 * network they are added automatically
	 * 
	 * @param link
	 */
	private void addLink(Link link) {
		if (link == null) {
			throw new RuntimeException("Can't add a null Link to the network");
		}
		if (link.from() == null || link.to() == null) {
			throw new RuntimeException("The link is not connecting anything.");
		}
		link.setNetwork((Network) this);

		// add link to links
		links.add(link);

		// if nodes in this link do not exist in the network
		// add them
		NodeIndex outIndex = nodeI.get(link.from());
		if (outIndex == null) {
			addNode(link.from());
			// now this index is not null
			outIndex = nodeI.get(link.from());
		}
		// lazy initialisation
		if (outIndex.outLinks == null) {
			if (directed) {
				outIndex.outLinks = new ArrayList<Link>();
			} else {
				outIndex.outLinks = (outIndex.inLinks != null) ? outIndex.inLinks
						: (outIndex.inLinks = new ArrayList<>());
			}
		}
		outIndex.outLinks.add(link);
		int linkFromIndex = outIndex.outLinks.size() - 1;

		NodeIndex inIndex = nodeI.get(link.to());
		if (inIndex == null) {
			addNode(link.to());
			inIndex = nodeI.get(link.to());
		}
		if (inIndex.inLinks == null) {
			if (directed)
				inIndex.inLinks = new ArrayList<>();
			else {

				inIndex.inLinks = (inIndex.outLinks != null) ? inIndex.outLinks
						: (inIndex.outLinks = new ArrayList<>());
			}
		}
		inIndex.inLinks.add(link);

		int linkToIndex = inIndex.inLinks.size() - 1;

		// Create a link index entry
		LinkIndex li = new LinkIndex(links.size() - 1, linkFromIndex,
				linkToIndex);
		linkI.put(link, li);

	}

	/**
	 * Add a node to the network if this does not exist
	 * 
	 * @param node
	 *            a node to be added
	 */
	public void addNode(final Node node) {
		// if the object already exists, do nothing
		if (nodeI.get(node) != null)
			return;

		nodes.add(node);

		// create node index
		NodeIndex ni = new NodeIndex(nodes.size() - 1, null, null);
		nodeI.put(node, ni);
	}

	private int nextNodeID = 0;
	private int nextLinkID = 0;

	public SimpleNode createNode() {
		return new SimpleNode(nextNodeID++);
	}

	public SimpleLink createLink(Node from, Node to) {
		return new SimpleLink(nextLinkID++, from, to);
	}

	/**
	 * Indexes Network Links. Each node has a set of in links and out links. if
	 * the network is undirected all the links are put in the in list
	 * 
	 */
	@SuppressWarnings("serial")
	public static class NodeIndex implements Serializable {
		// index of node in nodes list
		public int nodeIndex;
		public ArrayList<Link> inLinks;
		public ArrayList<Link> outLinks;

		public NodeIndex(final int nodeIndex, final ArrayList<Link> inLinks,
				final ArrayList<Link> outLinks) {
			this.nodeIndex = nodeIndex;
			this.outLinks = outLinks;
			this.inLinks = inLinks;
		}
	}

	/**
	 * Indexes Links in the link array and in the outLinks and inLinks from the
	 * Node index
	 * 
	 */
	@SuppressWarnings("serial")
	public static class LinkIndex implements Serializable {
		public int linkIndex;
		public int fromIndex;
		public int toIndex;

		public LinkIndex(final int linkIndex, final int fromIndex,
				final int toIndex) {
			this.linkIndex = linkIndex;
			this.fromIndex = fromIndex;
			this.toIndex = toIndex;
		}

	}

	/**
	 * <p>
	 * UTILITY METHOD
	 * </p>
	 * <p>
	 * Copies the current network to a new given instance
	 * </p>
	 * 
	 * @param newNetwork
	 *            the new network instance
	 */
	private FastNetwork copyTo(FastNetwork newNetwork) {
		newNetwork.nodes = new ArrayList<>(nodes.size());
		// copy nodes
		for (Node node : nodes) {
			newNetwork.nodes.add(node.getCopy());
		}
		newNetwork.links = new ArrayList<>(links.size());
		// copy links
		for (Link link : links) {
			newNetwork.links.add(link.getCopy());
		}

		int numNodes = nodes.size();

		Iterator<NodeIndex> indexIterator = nodeI.values().iterator();
		FastNetwork.NodeIndex[] indexArray = new FastNetwork.NodeIndex[numNodes];

		// copy link index structure
		for (int i = 0; i < numNodes; i++) {
			NodeIndex oldIndex = indexIterator.next();
			int nodeIndex = oldIndex.nodeIndex;

			ArrayList<Link> outLinkCopy = oldIndex.outLinks == null ? new ArrayList<Link>()
					: new ArrayList<Link>(oldIndex.outLinks.size());
			ArrayList<Link> inLinkCopy = directed ? (oldIndex.inLinks == null ? new ArrayList<Link>()
					: new ArrayList<Link>(oldIndex.inLinks.size()))
					: outLinkCopy;

			NodeIndex indexCopy = new NodeIndex(nodeIndex, inLinkCopy,
					outLinkCopy);

			// copy the index to the new network
			newNetwork.nodeI.put(nodes.get(i), indexCopy);
			indexArray[i] = oldIndex;
		}

		// copy Links to the index
		for (int i = 0; i < numNodes; i++) {
			NodeIndex indexi = indexArray[i];
			Node nodei = nodes.get(indexi.nodeIndex);

			if (indexi.outLinks != null) {
				for (Link link : indexi.outLinks) {
					if (directed || link.from().equals(nodei)) {
						newNetwork.addLink(link.getCopy());
					}
				}
			}
		}

		return newNetwork;
	}

}
