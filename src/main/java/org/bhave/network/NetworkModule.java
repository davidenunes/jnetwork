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
package org.bhave.network;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.DynamicNetwork;
import org.bhave.network.api.Network;
import org.bhave.network.impl.hash.dynamic.DynamicFastNetwork;
import org.bhave.network.model.BAForestModel;
import org.bhave.network.model.BAModel;
import org.bhave.network.model.ERModel;
import org.bhave.network.model.GilbertModel;
import org.bhave.network.model.KRegularModel;
import org.bhave.network.model.WSModel;
import org.bhave.network.model.impl.DefaultBAForestModel;
import org.bhave.network.model.impl.DefaultBAModel;
import org.bhave.network.model.impl.DefaultKRegularModel;
import org.bhave.network.model.impl.DefaultWSModel;
import org.bhave.network.model.impl.EERModel;
import org.bhave.network.model.impl.EGilberModel;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.bhave.network.api.DirectedNetwork;
import org.bhave.network.api.UndirectedNetwork;
import org.bhave.network.impl.fast.FastNetwork;

/**
 * <p>
 * This defines a <a href="http://code.google.com/p/google-guice"> GUICE </a>
 * module that handles the configuration of network library dependencies, hence
 * eliminating the need for factories in this library.
 * </p>
 *
 *
 * <p>
 * <h2>Getting a GUICE injector</h2>
 * To access multiple instances of our API objects such as a {@link Network
 * network} instance without accessing specific implementations, we build our
 * GUICE injector from this module like so: <br />
 * <br />
 *
 * <code>Injector injector = Guice.createInjector(new NetworkModule());</code>
 *
 * </p>
 *
 * <p>
 * From this point on you can get API objects like this: <br>
 * <br>
 * <code>Network network = injector.getInstance(Network.class);</code>
 * </p>
 *
 *
 * @author Davide Nunes
 */
public class NetworkModule extends AbstractModule {

    @Override
    protected void configure() {
        // Network API
        bind(Network.class).to(FastNetwork.class);

        bind(DynamicNetwork.class).to(DynamicFastNetwork.class);

        // NetworkModel API
        bind(Configuration.class).to(PropertiesConfiguration.class);

        bind(RandomGenerator.class).to(MersenneTwister.class);

        bind(BAModel.class).to(DefaultBAModel.class);
        bind(BAForestModel.class).to(DefaultBAForestModel.class);
        bind(KRegularModel.class).to(DefaultKRegularModel.class);
        bind(ERModel.class).to(EERModel.class);
        bind(GilbertModel.class).to(EGilberModel.class);
        bind(WSModel.class).to(DefaultWSModel.class);
    }

    @Provides
    DirectedNetwork provideDirectedNetwork() {
        return (DirectedNetwork) new FastNetwork(true);
    }

    @Provides
    UndirectedNetwork provideUndirectedNetwork() {
        return (UndirectedNetwork) new FastNetwork(false);
    }
}
