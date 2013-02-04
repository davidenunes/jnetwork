/**
 * 
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * This file is part of network-api.
 *
 * network-api is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * The network-api is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with network-api.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

package org.bhave.network.model.impl;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.math3.random.RandomGenerator;
import org.bhave.network.api.Network;
import org.bhave.network.model.NetworkModel;

/**
 * @author davide
 * 
 */
public abstract class AbstractNetworkModel implements NetworkModel {
	protected static final String PARAM_SEED = "seed";

	// common to all network generator implementations
	protected final Configuration config;
	protected final RandomGenerator random;
	protected final Network network;

	public AbstractNetworkModel(Configuration config, RandomGenerator random,
			Network network) {
		this.config = defaultConfiguration(config);
		this.random = random;
		this.network = network;

		
	}

	abstract Configuration defaultConfiguration(Configuration config);
	
	@Override
	public Configuration getConfiguration() {
		return this.config;
	}

}
