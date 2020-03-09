/**
 * 
 */
package com.issue.output;

import java.util.Optional;

import com.issue.model.Feature;
import com.issue.utils.Features;

/**
 * The Class Feature2Csv.
 *
 * @author branislav.beno
 */
public class Feature2Csv {

	/** The feature. */
	Feature feature;

	/**
	 * Instantiates a new feature 2 csv.
	 *
	 * @param feature the feature
	 */
	public Feature2Csv(Feature feature) {
		this.feature = feature;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// team
		sb.append(feature.getTeam() + ";");
		// key
		sb.append(feature.getKey() + ";");
		// feature
		sb.append(Optional.ofNullable(feature.getFeatureSummary()).orElse("n/a") + ";");
		// estimated
		sb.append(Features.printDefault(feature.getEstimated()) + ";");
		// opened
		sb.append(Features.printDefault(feature.getOpened()) + ";");
		// inProgress
		sb.append(Features.printDefault(feature.getInProgress()) + ";");
		// closed
		sb.append(Features.printDefault(feature.getClosed()) + ";");
		// done
		sb.append(Features.printDefault(feature.getDone()) + ";");
		// story points
		sb.append(Features.printDefault(feature.getStoryPoints()) + ";");
		// stories defined
		sb.append(feature.isStoriesDefined() + ";");

		return sb.append("\n").toString();
	}
}
