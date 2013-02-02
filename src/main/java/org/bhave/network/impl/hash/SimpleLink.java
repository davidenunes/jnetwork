/**
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * This file is part of the b-have network library.
 * 
 * The b-have network library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The b-have network library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the b-have network library.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.network.impl.hash;

import java.util.Properties;

import org.bhave.network.api.Link;

/**
 * Implementation of {@link Link}
 * 
 * @author Davide Nunes
 */
public class SimpleLink implements Link {
	private int id;
	private Properties properties;

	private double value;

	public SimpleLink(int id) {
		this.id = id;
		properties = new Properties();
	}

	/**
	 * Copy Constructor. Creates a link by copying an existing Link. This is to
	 * avoid the clone
	 * 
	 * @param link
	 *            a link to be copied
	 */
	public SimpleLink(SimpleLink link) {
		this(link.getID());
		this.value = link.value;

		for (Object key : link.properties.keySet()) {
			this.properties.put(key, link.properties.get(key));
		}
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);

	}

	@Override
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	@Override
	public String toString() {
		return "Link [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleLink other = (SimpleLink) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public double getValue() {
		return this.value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;

	}

}
