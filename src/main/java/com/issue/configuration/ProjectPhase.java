package com.issue.configuration;

import java.util.Optional;

/**
 * The Class ProjectPhase.
 */
public class ProjectPhase {

	/** The output file name for html. */
	private String outputFileName4Html;

	/** The output file name for csv. */
	private String outputFileName4Csv;

	/** The features query. */
	private String featuresQuery;

	/** The stories query. */
	private String storiesQuery;

	/**
	 * Instantiates a new project phase.
	 *
	 * @param featuresQuery the features query
	 * @param storiesQuery  the stories query
	 */
	public ProjectPhase(String featuresQuery, String storiesQuery) {
		this.featuresQuery = Optional.ofNullable(featuresQuery).orElseThrow(IllegalArgumentException::new);
		this.storiesQuery = Optional.ofNullable(storiesQuery).orElseThrow(IllegalArgumentException::new);
	}

	/**
	 * Gets the output file name for html.
	 *
	 * @return the output file name for html
	 */
	public String getOutputFileName4Html() {
		return outputFileName4Html;
	}

	/**
	 * Sets the output file name 4 html.
	 *
	 * @param outputFileName4Html the new output file name 4 html
	 */
	public void setOutputFileName4Html(String outputFileName4Html) {
		this.outputFileName4Html = Optional.ofNullable(outputFileName4Html).orElse(null);
	}

	/**
	 * Gets the output file name for csv.
	 *
	 * @return the output file name for csv
	 */
	public String getOutputFileName4Csv() {
		return outputFileName4Csv;
	}

	/**
	 * Sets the output file name 4 csv.
	 *
	 * @param outputFileName4Csv the new output file name 4 csv
	 */
	public void setOutputFileName4Csv(String outputFileName4Csv) {
		this.outputFileName4Csv = Optional.ofNullable(outputFileName4Csv).orElse(null);
	}

	/**
	 * Gets the features query.
	 *
	 * @return the features query
	 */
	public String getFeaturesQuery() {
		return featuresQuery;
	}

	/**
	 * Sets the features query.
	 *
	 * @param featuresQuery the new features query
	 */
	public void setFeaturesQuery(String featuresQuery) {
		this.featuresQuery = Optional.ofNullable(featuresQuery).orElse("");
	}

	/**
	 * Gets the stories query.
	 *
	 * @return the stories query
	 */
	public String getStoriesQuery() {
		return storiesQuery;
	}

	/**
	 * Sets the stories query.
	 *
	 * @param storiesQuery the new stories query
	 */
	public void setStoriesQuery(String storiesQuery) {
		this.storiesQuery = Optional.ofNullable(storiesQuery).orElse("");
	}
}
