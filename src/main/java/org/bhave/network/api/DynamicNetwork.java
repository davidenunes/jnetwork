package org.bhave.network.api;

import java.util.Set;

public interface DynamicNetwork extends Network {

	/**
	 * Sets the current instance of this network to a given discrete time t.
	 * Does Nothing if t is negative.
	 * 
	 * @param t
	 *            a discrete time with t >= 0
	 */
	public void setTime(int t);

	/**
	 * Returns the current time instant in which you are working
	 * 
	 * @return t a discrete time
	 */
	public int getTime();

	/**
	 * Returns the set of time instances for which there are events of node /
	 * link insertion
	 * 
	 * @return
	 */
	public Set<? extends Integer> getTimeInstances();
}
