/**
 *
 * Copyright 2013 Davide Nunes Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * This file is part of network-api.
 *
 * network-api is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * The network-api is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * network-api. If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.network.model;

import org.apache.commons.configuration.ConfigurationException;

/**
 *
 * @author Davide Nunes
 *
 */
public interface GilbertModel extends NetworkModel {

    public static final String P_NUM_NODES = "numNodes";
    public static final String P_P = "p";

    void configure(int numNodes, double p, long seed) throws ConfigurationException;
}
