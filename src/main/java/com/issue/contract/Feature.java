/**
 * 
 */
package com.issue.contract;

import com.issue.enums.Status;

/**
 * The Interface Feature.
 *
 * @author benito
 */
public interface Feature {

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	String getKey();

	/**
	 * Gets the feature summary.
	 *
	 * @return the feature summary
	 */
	String getFeatureSummary();

	/**
	 * Gets the team.
	 *
	 * @return the team
	 */
	String getTeam();

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	Status getStatus();

	/**
	 * Gets the estimated.
	 *
	 * @return the estimated
	 */
	Integer getEstimated();

	/**
	 * Gets the opened.
	 *
	 * @return the opened
	 */
	Integer getOpened();

	/**
	 * Gets the in progress.
	 *
	 * @return the in progress
	 */
	Integer getInProgress();

	/**
	 * Gets the closed.
	 *
	 * @return the closed
	 */
	Integer getClosed();

	/**
	 * Gets the done.
	 *
	 * @return the done
	 */
	Double getDone();

	/**
	 * Gets the raw done.
	 *
	 * @return the raw done
	 */
	Double getRawDone();

	/**
	 * Sets the feature summary.
	 *
	 * @param featureSummary the new feature summary
	 */
	void setFeatureSummary(String featureSummary);

	/**
	 * Sets the team.
	 *
	 * @param team the new team
	 */
	void setTeam(String team);

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	void setStatus(Status status);

	/**
	 * Checks if is stories defined.
	 *
	 * @return true, if is stories defined
	 */
	boolean isStoriesDefined();

	/**
	 * Sets the estimated.
	 *
	 * @param estimated the new estimated
	 */
	void setEstimated(Integer estimated);

	/**
	 * Inc estimated.
	 *
	 * @param increment the increment
	 */
	void incEstimated(int increment);

	/**
	 * Sets the opened.
	 *
	 * @param opened the new opened
	 */
	void setOpened(Integer opened);

	/**
	 * Inc opened.
	 *
	 * @param increment the increment
	 */
	void incOpened(int increment);

	/**
	 * Sets the in progress.
	 *
	 * @param inProgress the new in progress
	 */
	void setInProgress(Integer inProgress);

	/**
	 * Inc in progress.
	 *
	 * @param increment the increment
	 */
	void incInProgress(int increment);

	/**
	 * Sets the closed.
	 *
	 * @param closed the new closed
	 */
	void setClosed(Integer closed);

	/**
	 * Inc closed.
	 *
	 * @param increment the increment
	 */
	void incClosed(int increment);
}
