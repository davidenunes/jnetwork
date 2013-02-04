/**
 * 
 */
package org.bhave.network.model.impl;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;
import org.bhave.network.api.Link;
import org.bhave.network.api.Network;
import org.bhave.network.api.Node;
import org.bhave.network.model.KRegularModel;
import org.bhave.network.model.NetworkModel;
import org.bhave.network.model.utils.NetworkModelUtils;

import com.google.inject.Inject;

/**
 * @author davide
 * 
 */
public class EWSModel extends AbstractNetworkModel implements NetworkModel {

	private static final String PARAM_NUM_NODES = "numNodes";
	private static final String PARAM_DEGREE = "d";
	private static final String PARAM_REATTACHP = "p";

	private static final KRegularModel model;

	@Inject
	public EWSModel(Configuration config, RandomGenerator random,
			Provider<Network> networkProvider, KRegularModel regularModel) {
		super(config, random, networkProvider);
		this.model = regularModel;
	}

	@Override
	void generateNetwork() {
		int n = config.getInt(PARAM_NUM_NODES);
		int d = config.getInt(PARAM_DEGREE);
		double p = config.getDouble(PARAM_REATTACHP);
	
		

	}

	@Override
	public void configure(Configuration configuration)
			throws ConfigurationException {
		int numNodes = configuration.getInt(PARAM_NUM_NODES);
		int d = configuration.getInt(PARAM_DEGREE);
		int p = configuration.getDouble(PARAM_REATTACHP);

		if (numNodes < 0)
			throw new ConfigurationException(PARAM_NUM_NODES
					+ "must be positive");

		if (d < 1 || d > ((numNodes - 1) / 2))
			throw new ConfigurationException(PARAM_DEGREE
					+ "must be within: 1 <= d <= ((n-1)/ 2)");
		if (p < 0 || p >= 1)
			throw new ConfigurationException(PARAM_REATTACHP
					+ "must be within: 0 <= p < 1");

	}

	@Override
	Configuration defaultConfiguration(Configuration config) {
		config.setProperty(PARAM_NUM_NODES, 10);
		config.setProperty(PARAM_DEGREE, 2);
		config.setProperty(PARAM_REATTACHP, 0.2);
		config.setProperty(PARAM_SEED, System.currentTimeMillis());
		return config;
	}

}
