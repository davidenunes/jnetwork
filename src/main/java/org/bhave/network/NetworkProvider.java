package org.bhave.network;

import org.bhave.network.api.Network;

import com.google.inject.Provider;
import org.bhave.network.impl.fast.FastNetwork;

/**
 * Used whenever we need multiple network instances. For instance, a network
 * model may need to reset its current network instance for new model
 * generations.
 *
 * @author Davide Nunes
 *
 */
public class NetworkProvider implements Provider<Network> {

    @Override
    public Network get() {
        return new FastNetwork();
    }
}
