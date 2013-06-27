/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhave.network;

import com.google.inject.Provider;
import org.bhave.network.api.DirectedNetwork;
import org.bhave.network.impl.fast.FastNetwork;

/**
 *
 * @author davide
 */
public class DirectedNetworkProvider implements Provider<DirectedNetwork> {
    @Override
    public DirectedNetwork get() {
        return (DirectedNetwork) new FastNetwork(true);
    }
}
