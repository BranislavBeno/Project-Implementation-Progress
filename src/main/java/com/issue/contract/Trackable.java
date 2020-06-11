/**
 * 
 */
package com.issue.contract;

import java.io.IOException;

import com.issue.configuration.GlobalParams;

/**
 * The Interface Trackable.
 *
 * @author branislav.beno
 */
public interface Trackable {

	/**
	 * Ask issue tracker.
	 *
	 * @param globalParams the global params
	 * @param query the query
	 * @param startAt the start at
	 * @param maxResults the max results
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	abstract String askIssueTracker(GlobalParams globalParams, String query, int startAt, int maxResults)
			throws IOException, InterruptedException;
}
