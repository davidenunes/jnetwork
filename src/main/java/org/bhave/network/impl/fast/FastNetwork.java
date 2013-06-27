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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import org.bhave.network.api.DirectedNetwork;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.api.UndirectedNetwork;

/**
 * Fast Implementation of {@link Network} interface.
 *
 * @author Davide Nunes
 */
public class FastNetwork implements DirectedNetwork, UndirectedNetwork, Network {

    private static final long serialVersionUID = 1L;
    boolean directed;
    // map node to index and links
    HashMap<Node, NodeIndex> nodeI;
    // map link to index in link
    HashMap<Link, LinkIndex> linkI;
    // store nodes
    ArrayList<Node> nodes;
    // store links
    ArrayList<Link> links;
    HashMap<NodePairKey, Set<Link>> nodePairI;

    // constructors
    public FastNetwork() {
        this(false);
    }

    public FastNetwork(boolean directed) {
        this.directed = directed;
        nodes = new ArrayList<>();
        links = new ArrayList<>();
        nodeI = new HashMap<>();
        linkI = new HashMap<>();
        nodePairI = new HashMap<>();
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
    @Override
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
    @Override
    public boolean addLink(Link link) {
        if (link == null) {
            throw new RuntimeException("Can't add a null Link to the network");
        }
        if (link.from() == null || link.to() == null) {
            throw new RuntimeException("The link is not connecting anything.");
        }
        link.setNetwork(this);

        // add link to links
        links.add(link);

        // IF NODES in this link do NOT EXIST in the network, ADD them
        NodeIndex outIndex = nodeI.get(link.from());
        if (outIndex == null) {
            //adds node and initialises its in-out link index
            addNode(link.from());
            // now this index is not null
            outIndex = nodeI.get(link.from());
        }
        // lazy initialisation
        if (outIndex.outLinks == null) {
            if (directed) {
                outIndex.outLinks = new ArrayList<>();
            } else {
                outIndex.outLinks = (outIndex.inLinks != null) ? outIndex.inLinks
                        : (outIndex.inLinks = new ArrayList<>());
            }
        }
        outIndex.outLinks.add(link);
        int linkFromIndex = outIndex.outLinks.size() - 1;

        NodeIndex inIndex = nodeI.get(link.to());
        if (inIndex == null) {
            //adds node and initialises its in-out link index
            addNode(link.to());
            inIndex = nodeI.get(link.to());
        }
        if (inIndex.inLinks == null) {
            inIndex.inLinks = new ArrayList<>();
        }
        inIndex.inLinks.add(link);

        int linkToIndex = inIndex.inLinks.size() - 1;

        // Create a link index entry
        LinkIndex li = new LinkIndex(links.size() - 1, outIndex,
                inIndex);
        linkI.put(link, li);

        //add link to node pair index
        addNodePairEntry(link);

        return true;
    }

    private void addNodePairEntry(Link link) {
        NodePairKey k = new NodePairKey(link.from(), link.to());

        Set<Link> linkset;
        if (!nodePairI.containsKey(k)) {
            linkset = new HashSet<>();
            nodePairI.put(k, linkset);
        } else {
            linkset = nodePairI.get(k);
        }
        linkset.add(link);
    }

    private void removeLinkFromNodePairI(Link link) {
        NodePairKey k = new NodePairKey(link.from(), link.to());
        Set<Link> linkset = nodePairI.get(k);
        linkset.remove(link);
        if (linkset.isEmpty()) {
            nodePairI.remove(k);
        }
    }

    /**
     * Add a node to the network if this does not exist
     *
     * @param node a node to be added
     */
    @Override
    public boolean addNode(final Node node) {
        // if the object already exists, do nothing
        if (nodeI.get(node) != null) {
            return false;
        }
        node.setNetwork(this);
        nodes.add(node);

        // create node index
        NodeIndex ni = new NodeIndex(nodes.size() - 1, null, null);
        nodeI.put(node, ni);
        return true;
    }
    /**
     * NODE AND LINK CREATION This class can create nodes and links on demand
     * making sure that the user does not have to worry about node and link
     * implementations or sequential ID values.
     */
    private int nextNodeID = 0;
    private int nextLinkID = 0;

    @Override
    public Node createNode() {
        SimpleNode newNode = new SimpleNode(nextNodeID++);

        return newNode;
    }

    @Override
    public Link createLink(Node from, Node to) {
        return new SimpleLink(nextLinkID++, from, to);
    }

    @Override
    public Link addLink(Node node1, Node node2) {

        Link newLink = createLink(node1, node2);
        addLink(newLink);
        return newLink;

    }

    @Override
    public boolean removeNode(Node node) {
        //get neighbours to delete the node to link index
        for (Node neighbour : getNeighbours(node)) {
            NodePairKey k = new NodePairKey(node, neighbour);
            nodePairI.remove(k);
        }

        //remove node from nodeList
        if (containsNode(node)) {
            //remove node from nodes list
            nodes.remove(nodeI.get(node).nodeIndex);

            //clean the node index and remove all the links attacked to the node
            NodeIndex nodeIndex = nodeI.get(node);

            Set<Link> linksToRemove = new HashSet<>();

            //remove all the links associated with the node
            if (directed) {
                if (nodeIndex.outLinks != null) {
                    //remove all the links in the out link list first
                    for (Link link : nodeIndex.outLinks) {
                        //remove from links
                        LinkIndex li = linkI.get(link);
                        links.remove(li.linkIndex);
                        //mark for removal remove the link index
                        linksToRemove.add(link);

                    }
                }
            }
            if (nodeIndex.outLinks != null) //remove all the links from the in link list
            {
                for (Link link : nodeIndex.inLinks) {
                    //remove from links
                    LinkIndex li = linkI.get(link);
                    links.remove(li.linkIndex);
                    //remove the link index
                    linksToRemove.add(link);
                }
            }
            for (Link linkToRemove : linksToRemove) {
                deleteLinkFromIndex(linkToRemove);
            }

            //remove this after the links are deleted from the indexes 

            nodeI.remove(node);

            return true;
        }
        return false;
    }

    /**
     * Removes a given link from the existing node indexes
     */
    private void deleteLinkFromIndex(Link link) {
        LinkIndex index = linkI.get(link);

        NodeIndex fromIndex = nodeI.get(link.from());
        NodeIndex toIndex = nodeI.get(link.to());

        //remove from the out links first
        if (directed) {
            if (fromIndex.outLinks != null) {
                fromIndex.outLinks.remove(index.fromIndex);
            }
            if (toIndex.outLinks != null) {
                toIndex.outLinks.remove(index.toIndex);
            }
        }
        //remove the links from the in links
        if (fromIndex.inLinks != null) {
            fromIndex.inLinks.remove(index.fromIndex);
        }
        if (toIndex.inLinks != null) {
            toIndex.inLinks.remove(index.toIndex);
        }

        //delete the link index entry, no longer needed
        linkI.remove(link);
    }

    @Override
    public boolean removeNode(int id) {
        //the equals works with the id so use this to check for its existence
        SimpleNode dummyNode = new SimpleNode(id);
        dummyNode.setNetwork(this);

        return removeNode(dummyNode);
    }

    @Override
    public boolean removeLink(Link link) {
        if (containsLink(link)) {
            //remove from links
            links.remove(linkI.get(link).linkIndex);

            //delete link from node indexes and delete link index
            deleteLinkFromIndex(link);

            //delete link from nodePair Entry
            removeLinkFromNodePairI(link);

            return true;
        }
        return false;
    }

    @Override
    public boolean removeLink(int id) {
        SimpleLink dummyLink = new SimpleLink(id, null, null);
        dummyLink.setNetwork(this);

        if (containsLink(dummyLink)) {
            Link linkToRemove = links.get(linkI.get(dummyLink).linkIndex);
            return removeLink(linkToRemove);
        }
        return false;
    }

    @Override
    public Node getNode(int id) {

        SimpleNode dummyNode = new SimpleNode(id);
        dummyNode.setNetwork(this);
        if (containsNode(dummyNode)) {
            return nodes.get(nodeI.get(dummyNode).nodeIndex);
        }
        return null;
    }

    @Override
    public Link getLink(int id) {
        SimpleLink dummyLink = new SimpleLink(id, null, null);
        dummyLink.setNetwork(this);

        if (containsLink(dummyLink)) {
            Link link = links.get(linkI.get(dummyLink).linkIndex);
            return link;
        }
        return null;
    }

    @Override
    public Collection<? extends Link> getLinks(Node node1, Node node2) {
        NodePairKey k = new NodePairKey(node1, node2);
        if (nodePairI.containsKey(k)) {
            return new HashSet<>(nodePairI.get(k));
        }
        return new HashSet<>();
    }

    @Override
    public Collection<? extends Link> getLinks(Node node) {
        if (containsNode(node)) {
            NodeIndex index = nodeI.get(node);
            Set<Link> result = new HashSet<>();
            if (index.inLinks != null) {
                result.addAll(index.inLinks);
            }
            if (index.outLinks != null) {
                result.addAll(index.outLinks);
            }

            return result;
        }
        return new HashSet<>();
    }

    @Override
    public Collection<? extends Link> getOutLinks(Node node) {
        if (containsNode(node)) {
            NodeIndex index = nodeI.get(node);
            Set<Link> result = new HashSet<>();
            if (directed) {
                if (index.outLinks != null) {
                    result.addAll(index.outLinks);
                }
            } else {
                //if the network is not directed inLinks = outLinks
                if (index.inLinks != null) {
                    result.addAll(index.inLinks);
                }
            }

            return result;
        }
        return new HashSet<>();
    }

    @Override
    public Collection<? extends Link> getInLinks(Node node) {
        if (containsNode(node)) {
            NodeIndex index = nodeI.get(node);
            Set<Link> result = new HashSet<>();

            //if the network is not directed inLinks = outLinks
            if (index.inLinks != null) {
                result.addAll(index.inLinks);
            }

            return result;
        }
        return new HashSet<>();
    }

    @Override
    public Collection<? extends Node> getSuccessors(Node node) {
        if (containsNode(node)) {
            Collection<? extends Link> outLinks = getOutLinks(node);
            Set<Node> successors = new HashSet<>();


            for (Link link : outLinks) {

                if (directed) {
                    successors.add(link.to());
                } else {
                    //remember that order does not matter in an undirected network
                    Node nodeToAdd = link.to().equals(node) ? link.from() : link.to();
                    successors.add(nodeToAdd);
                }
            }
            return successors;
        }
        return new HashSet<>();
    }

    @Override
    public Collection<? extends Node> getPredecessors(Node node) {
        if (containsNode(node)) {
            Collection<? extends Link> outLinks = getInLinks(node);
            Set<Node> predecessors = new HashSet<>();


            for (Link link : outLinks) {
                if (directed) {
                    predecessors.add(link.from());
                } else {
                    //remember that order does not matter in an undirected network
                    Node nodeToAdd = link.to().equals(node) ? link.from() : link.to();
                    predecessors.add(nodeToAdd);
                }
            }
            return predecessors;
        }
        return new HashSet<>();
    }

    @Override
    public Collection<? extends Node> getNeighbours(Node node) {
        if (containsNode(node)) {
            Set<Node> neighbours = new HashSet<>();
            Collection<? extends Link> linksToNeighbours;

            linksToNeighbours = getLinks(node);

            for (Link link : linksToNeighbours) {
                Node nodeToAdd = link.to().equals(node) ? link.from() : link.to();
                neighbours.add(nodeToAdd);
            }
            return neighbours;
        }
        return new HashSet<>();
    }

    @Override
    public Collection<? extends Node> getNeighbours(int id) {
        SimpleNode dummyNode = new SimpleNode(id);
        dummyNode.setNetwork(this);
        return getNeighbours(dummyNode);
    }

    @Override
    public Collection<? extends Node> getNodes() {
        return new ArrayList<>(nodes);
    }

    @Override
    public Collection<? extends Link> getLinks() {
        return new ArrayList<>(links);
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
        return node != null && nodeI.containsKey(node) && node.getNetwork() == this;
    }

    @Override
    public boolean containsLink(Link link) {
        return link != null && linkI.containsKey(link) && link.getNetwork() == this;
    }

    @Override
    public boolean containsLinks(Node node1, Node node2) {
        NodePairKey k = new NodePairKey(node1, node2);
        return nodePairI.containsKey(k);
    }

    /**
     * Indexes Network Links. Each node has a set of in links and out links. If
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
        //index of this link in the nodeIndex in or out array of links

        public int linkIndex;
        public NodeIndex fromIndex;
        public NodeIndex toIndex;

        public LinkIndex(final int linkIndex, final NodeIndex fromIndex,
                final NodeIndex toIndex) {
            this.linkIndex = linkIndex;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;

        }
    }

    /**
     * <p> UTILITY METHOD </p> <p> Copies the current network to a new given
     * instance </p>
     *
     * @param newNetwork the new network instance
     */
    private FastNetwork copyTo(FastNetwork newNetwork) {
        newNetwork.nodes = new ArrayList<>(nodes.size());
        // copy nodes
        for (Node node : nodes) {
            Node nodeCopy = node.getCopy();
            newNetwork.addNode(nodeCopy);
        }
        newNetwork.links = new ArrayList<>(links.size());

        int numNodes = nodes.size();

        Iterator<NodeIndex> nodeIIterator = nodeI.values().iterator();
        FastNetwork.NodeIndex[] nodeIArray = new FastNetwork.NodeIndex[numNodes];

        // copy node to link index structure
        for (int i = 0; i < numNodes; i++) {
            NodeIndex oldIndex = nodeIIterator.next();
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
            nodeIArray[i] = oldIndex;
        }

        // Copy links to index
        for (int i = 0; i < numNodes; i++) {
            NodeIndex indexi = nodeIArray[i];
            Node nodei = nodes.get(indexi.nodeIndex);

            if (indexi.outLinks != null) {
                for (Link link : indexi.outLinks) {
                    if (directed || link.from().equals(nodei)) {
                        Link linkCopy = link.getCopy();
                        linkCopy.setNetwork(newNetwork);
                        newNetwork.addLink(linkCopy);
                    }
                }
            }
        }
        return newNetwork;
    }

    private static class NodePairKey {

        private Node from;
        private Node to;

        public NodePairKey(Node from, Node to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            int hash1 = 29 * hash + Objects.hashCode(this.from);

            int hash2 = 29 * hash + Objects.hashCode(this.to);

            return hash1 * hash2;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final NodePairKey other = (NodePairKey) obj;
            if ((Objects.equals(this.from, other.from) && Objects.equals(this.to, other.to))
                    || (Objects.equals(this.from, other.to) && Objects.equals(this.to, other.from))) {
                return true;
            }
            return false;
        }
    }
}
