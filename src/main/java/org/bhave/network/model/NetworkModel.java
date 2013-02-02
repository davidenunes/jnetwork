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

package org.bhave.network.model;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.bhave.network.api.Network;

/**
 * Defines an interface for Network models.
 * 
 * <p>
 * A network model can be seen as a {@link Network Network} factory. Instances
 * of this interface should be able to create network instances based on
 * algorithms for constructing network topologies with a target set of
 * topological features.
 * </p>
 * 
 * <p>
 * Among such generative procedures, we can include simple or <b>complex network
 * models</b>. Complex network models define ways to construct networks with
 * non-trivial topological features that do not occur in simple networks such as
 * lattices or random graphs but often occur in real graphs such as Online
 * Social networks. </b>
 * 
 * <h2>Network Model Configuration</h2>
 * <p>
 * A <code>NetworkModel</code> should provide a way to configure the network
 * generative procedure using the method
 * {@link NetworkModel#configure(Configuration)}. The specific details of each
 * configuration (such as parameter names) should be included within each
 * network model.
 * <p>
 * 
 * The network models are configured using {@link Configuration} objects. These
 * should be provided by the network model implementations using the
 * {@link NetworkModel#getConfiguration()} method.
 * 
 * If you use the general configure method from the {@link NetworkModel}
 * interface, you could setup a model as follows:
 * 
 * <br>
 * <br>
 * <code>
 * Configuration config = model.getConfiguration();<br>
 * config.setProperty("numNodes", 1000);<br>
 * config.setProperty("numLinks", 2);<br><br>
 * 
 * model.configure(config);
 * </code> <br>
 * 
 * 
 * @author Davide Nunes
 * 
 */
public interface NetworkModel {
	/**
	 * <p>
	 * Returns a configuration object which can be used to set parameter values
	 * for the network model. After getting a configuration object and setting
	 * the appropriate values, one can configure the model with the method
	 * {@link NetworkModel#configure(Configuration)}.
	 * </p>
	 * 
	 * @return a configuration instance which should have preferably default
	 *         values for the necessary parameters.
	 * 
	 */
	Configuration getConfiguration();

	/**
	 * <p>
	 * Configures the {@link NetworkModel} with a given {@link Configuration}
	 * instance. Note that the configuration objects should be provided by each
	 * network model instance and accessed using @
	 * NetworkModel#getConfiguration()}.
	 * </p>
	 * 
	 * @param configuration
	 *            a configuration with the appropriate parameters for the
	 *            network model to run.
	 * 
	 * @throws ConfigurationException
	 *             an exception that should be thrown if the passed
	 *             configuration contains an invalid configuration (missing
	 *             parameters or invalid parameter values)
	 */
	void configure(Configuration configuration)
			throws ConfigurationException;

	/**
	 * Generates a {@link Network} instance. Note that this method should only
	 * be called after the {@link NetworkModel} is configured properly.
	 * 
	 * To correctly configure a model, you should refer to the documentation of
	 * each model. The various models should supply enough information in their
	 * documentation about what kind of parameters are expected and the valid
	 * values for each parameter.
	 * 
	 * @return network a network instance
	 */
	Network generate();
}
