/**
 * 
 */
package com.issue.configuration;

import java.util.List;
import java.util.Optional;

/**
 * The Class GlobalParams.
 *
 * @author benito
 */
public class GlobalParams {

	/** The instance. */
	private static GlobalParams instance;

	/** The issue tracker uri. */
	private String issueTrackerUri;

	/** The issue uri. */
	private String issueUri;

	/** The epic report uri. */
	private String epicReportUri;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The output file name 4 xlsx. */
	private String outputFileName4Xlsx;

	/** The phases. */
	private List<ProjectPhase> phases;

	/**
	 * Instantiates a new global params.
	 */
	public GlobalParams() {
		// Basic constructor is empty
	}

	/**
	 * Gets the single instance of GlobalParams.
	 *
	 * @return single instance of GlobalParams
	 */
	public static GlobalParams getInstance() {
		if (instance == null) {
			instance = new GlobalParams();
		}
		return instance;
	}

	/**
	 * Gets the issue tracker uri.
	 *
	 * @return the issueTrackerUri
	 */
	public String getIssueTrackerUri() {
		return issueTrackerUri;
	}

	/**
	 * Sets the issue tracker uri.
	 *
	 * @param issueTrackerUri the issueTrackerUri to set
	 */
	public void setIssueTrackerUri(String issueTrackerUri) {
		this.issueTrackerUri = Optional.ofNullable(issueTrackerUri).orElse("");
	}

	/**
	 * Gets the issue uri.
	 *
	 * @return the issueUri
	 */
	public String getIssueUri() {
		return issueUri;
	}

	/**
	 * Sets the issue uri.
	 *
	 * @param issueUri the issueUri to set
	 */
	public void setIssueUri(String issueUri) {
		this.issueUri = Optional.ofNullable(issueUri).orElse("");
	}

	/**
	 * Gets the epic report uri.
	 *
	 * @return the epicReportUri
	 */
	public String getEpicReportUri() {
		return epicReportUri;
	}

	/**
	 * Sets the epic report uri.
	 *
	 * @param epicReportUri the epicReportUri to set
	 */
	public void setEpicReportUri(String epicReportUri) {
		this.epicReportUri = Optional.ofNullable(epicReportUri).orElse("");
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the phases.
	 *
	 * @return the phases
	 */
	public Optional<List<ProjectPhase>> getPhases() {
		return Optional.ofNullable(phases);
	}

	/**
	 * Sets the phases.
	 *
	 * @param phases the phases to set
	 */
	public void setPhases(List<ProjectPhase> phases) {
		this.phases = phases;
	}

	/**
	 * Gets the output file name 4 xlsx.
	 *
	 * @return the outputFileName4Xlsx
	 */
	public String getOutputFileName4Xlsx() {
		return outputFileName4Xlsx;
	}

	/**
	 * Sets the output file name 4 xlsx.
	 *
	 * @param outputFileName4Xlsx the outputFileName4Xlsx to set
	 */
	public void setOutputFileName4Xlsx(String outputFileName4Xlsx) {
		this.outputFileName4Xlsx = Optional.ofNullable(outputFileName4Xlsx).orElse(null);
	}
}
