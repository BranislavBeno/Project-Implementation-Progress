/**
 * 
 */
package com.issue.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.issue.configuration.GlobalParams;
import com.issue.contract.IFeatureDao;
import com.issue.dao.FeatureDao;
import com.issue.enums.Status;
import com.issue.model.Feature;
import com.issue.tracking.IssueStrategy;

/**
 * The Class Features.
 *
 * @author benito
 */
public class Features {

	/** The scrum team field id. */
	public static final String SCRUM_TEAM_FIELD_ID = "customfield_17555";

	/** The feature name field id. */
	public static final String FEATURE_NAME_FIELD_ID = "customfield_12641";

	/**
	 * Utility classes should not have public constructors.
	 */
	private Features() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Prints the Integer default.
	 *
	 * @param val the val
	 * @return the string
	 */
	public static String printDefault(final Integer val) {
		String s = "n/a";
		if (val != null)
			return val.toString();
		return s;
	}

	/**
	 * Prints the Double default.
	 *
	 * @param val the val
	 * @return the string
	 */
	public static String printDefault(final Double val) {
		String s = "n/a";
		if (val != null)
			return String.valueOf(val.intValue()) + "%";
		return s;
	}

	/**
	 * Checks if string contains only digits.
	 *
	 * @param s the s
	 * @return true, if is number
	 */
	public static boolean isNumber(final String s) {
		boolean isNumber = true;
		for (char c : s.toCharArray()) {
			isNumber = isNumber && Character.isDigit(c);
		}
		return isNumber;
	}

	/**
	 * Status contains.
	 *
	 * @param value the value
	 * @return true, if successful
	 */
	public static boolean statusContains(final String value) {
		for (Status status : Status.values()) {
			if (status.name().equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Extract features.
	 *
	 * @param jsonString the json string
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static IFeatureDao<String, Feature> extractFeatures(final String jsonString) throws IOException {
		// Create empty features map
		IFeatureDao<String, Feature> features = new FeatureDao();

		// Get json node "issues"
		JsonNode issuesNode = new ObjectMapper().readTree(jsonString).get("issues");

		// run through issues
		Optional.ofNullable(issuesNode).ifPresent(i -> i.forEach(issue -> {
			// Get key
			String key = issue.get("key").textValue();

			// Get json node "fields"
			JsonNode issueFields = issue.get("fields");

			// Get issue summary
			String summary = issueFields.get(FEATURE_NAME_FIELD_ID).asText();

			// Get json node scrum team
			JsonNode scrumTeam = issueFields.get(SCRUM_TEAM_FIELD_ID);
			// Get SCRUM team
			String team = scrumTeam.get("value").asText();

			// Get json node "fields.status"
			JsonNode statusField = issueFields.get("status");
			// Get issue status
			String status = statusField.get("name").asText().replace(' ', '_');

			// Add new feature into map
			features.save(new Feature.Builder().feature(summary).key(key).team(team)
					.status(Status.valueOf(status.toUpperCase())).build());
		}));

		return features;
	}

	/**
	 * Sort features list.
	 *
	 * @param values the values
	 */
	private static void sortFeaturesList(List<Feature> values) {
		// sort features according to feature summary
		Collections.sort(values, (a, b) -> a.getFeatureSummary().compareToIgnoreCase(b.getFeatureSummary()));
		// sort features according to Scrum team
		Collections.sort(values, (a, b) -> a.getTeam().compareTo(b.getTeam()));
	}

	/**
	 * Prepare filtered features list.
	 *
	 * @param dao the dao
	 * @return the list
	 */
	public static List<Feature> prepareFilteredFeaturesList(final IFeatureDao<String, Feature> dao) {
		// prepare features list
		List<Feature> values = dao.getAll().values().stream().filter(Feature::isStoriesDefined)
				.collect(Collectors.toList());

		// sort features list
		sortFeaturesList(values);

		return List.copyOf(values);
	}

	/**
	 * Prepare filtered features list.
	 *
	 * @param dao the dao
	 * @return the list
	 */
	public static List<Feature> prepareFeaturesList(final IFeatureDao<String, Feature> dao) {
		// prepare features list
		List<Feature> values = dao.getAll().values().stream().collect(Collectors.toList());

		// sort features list
		sortFeaturesList(values);

		return List.copyOf(values);
	}

	/**
	 * Creates the request uri.
	 *
	 * @param globalParams the global params
	 * @param startAt      the start at
	 * @param maxResults   the max results
	 * @param fields       the fields
	 * @return the string
	 */
	public static String createRequestUri(GlobalParams globalParams, int startAt, int maxResults, String fields) {
		StringBuilder sb = new StringBuilder();
		sb.append(globalParams.getIssueTrackerUri()).append("?").append("jql=")
				.append(Utils.prepareUrl(globalParams.getFeaturesQuery())).append("&maxResults=").append(maxResults)
				.append("&fields=").append(fields).append("&startAt=").append(startAt);
		return sb.toString();
	}

	/**
	 * Creates the features repo.
	 *
	 * @param globalParams the global params
	 * @return the i feature dao
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static IFeatureDao<String, Feature> createFeaturesRepo(GlobalParams globalParams)
			throws IOException, InterruptedException {

		// Set initials for stories gathering
		int startAt = 0;
		int maxResults = 1000;
		int totalCount = 0;

		// Create empty features list
		IFeatureDao<String, Feature> features = new FeatureDao();

		do {
			// Get json response
			String jsonFeatures = IssueStrategy.FEATURES.askIssueTracker(globalParams, startAt, maxResults);

			// Extract features from json
			features.saveAll(Features.extractFeatures(jsonFeatures).getAll());

			// Get json node "total"
			JsonNode total = new ObjectMapper().readTree(jsonFeatures).get("total");

			// Get total issues count
			if (total != null && total.isInt())
				totalCount = total.intValue();

			// Increment starting position for json issues pagination
			startAt += maxResults;

		} while (startAt < totalCount);

		return features;
	}
}
