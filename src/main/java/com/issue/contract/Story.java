/**
 * 
 */
package com.issue.contract;

import java.util.Optional;

import com.issue.enums.Status;

/**
 * The Interface Story.
 *
 * @author benito
 */
public interface Story {

	/**
	 * Gets the epic.
	 *
	 * @return the epic
	 */
	Optional<String> getEpic();

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	Optional<Status> getStatus();

	/**
	 * Gets the story points.
	 *
	 * @return the story points
	 */
	Optional<Integer> getStoryPoints();
}
