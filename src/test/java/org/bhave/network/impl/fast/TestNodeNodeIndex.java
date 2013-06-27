/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhave.network.impl.fast;

import java.util.Objects;
import org.bhave.network.api.Node;


import org.junit.Test;


import static org.junit.Assert.*;

/**
 *
 * @author davide
 */
public class TestNodeNodeIndex {

    @Test
    public void testEquals() {
        NodePairIndex index = new NodePairIndex(new SimpleNode(0), new SimpleNode(1));
        NodePairIndex index2 = new NodePairIndex(new SimpleNode(1), new SimpleNode(0));
        NodePairIndex index3 = new NodePairIndex(new SimpleNode(0), new SimpleNode(2));


        assertTrue(index.equals(index2));
        assertFalse(index.equals(index3));

        assertTrue(index.hashCode() == index2.hashCode());
        assertFalse(index.hashCode() == index3.hashCode());
    }

    private static class NodePairIndex {

        private Node from;
        private Node to;

        public NodePairIndex(Node from, Node to) {
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
            final NodePairIndex other = (NodePairIndex) obj;
            if ((Objects.equals(this.from, other.from) && Objects.equals(this.to, other.to))
                    || (Objects.equals(this.from, other.to) && Objects.equals(this.to, other.from))) {
                return true;
            }
            return false;
        }
    }
}
