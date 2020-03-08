/**
 * 
 */
package com.issue.utils;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.issue.configuration.GlobalParams;
import com.issue.contract.IFeatureDao;
import com.issue.contract.IStoryDao;
import com.issue.dao.StoryDao;
import com.issue.enums.Status;
import com.issue.model.Feature;
import com.issue.model.Story;
import com.issue.tracking.IssueStrategy;

/**
 * The Class Utils.
 *
 * @author branislav.beno
 */
public class Stories {

	/** The Constant STORY_POINTS_FIELD_ID. */
	public static final String STORY_POINTS_FIELD_ID = "customfield_10002";

	/** The Constant EPIC_LINK_FIELD_ID. */
	public static final String EPIC_LINK_FIELD_ID = "customfield_12640";

	/**
	 * Utility classes should not have public constructors.
	 */
	private Stories() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Extract story points.
	 *
	 * @param issueField the issue field
	 * @return the int
	 */
	private static int extractStoryPoints(JsonNode issueField) {
		// Get json node story points
		JsonNode storyPoints = Optional.ofNullable(issueField.get(STORY_POINTS_FIELD_ID))
				.orElseGet(() -> new ObjectNode(null));

		return storyPoints.asInt(0);
	}

	/**
	 * Extract epic link.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 */
	private static String extractEpicLink(JsonNode issueFields) {
		// Initialize epic link text
		String link = "";

		// Get json node epic link
		if (issueFields.get(EPIC_LINK_FIELD_ID) != null) {
			link = issueFields.get(EPIC_LINK_FIELD_ID).asText();
		}

		return link;
	}

	/**
	 * Extract issue status.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	private static String extractIssueStatus(JsonNode issueFields) {
		// Initialize status
		String status = Status.READY_FOR_REFINEMENT.name();

		// Get json node "fields.status"
		JsonNode statusField = Optional.ofNullable(issueFields.get("status")).orElse(new ObjectNode(null));

		// Get status name
		if (statusField.get("name") != null) {
			status = statusField.get("name").asText().replace(' ', '_');
		}

		return status;
	}

	/**
	 * Extract stories.
	 *
	 * @param jsonString the json string
	 * @return the i story dao
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static IStoryDao<Story> extractStories(final String jsonString) throws IOException {
		// Create empty stories list
		IStoryDao<Story> stories = new StoryDao();

		// Get json node "issues"
		JsonNode issuesNode = new ObjectMapper().readTree(jsonString).get("issues");

		// run through issues
		Optional.ofNullable(issuesNode).ifPresent(f -> f.forEach(issue -> {
			// Get json node "fields"
			JsonNode issueFields = issue.get("fields");

			// Get story points
			int sp = extractStoryPoints(issueFields);

			// Get epic link
			String epic = extractEpicLink(issueFields);

			// Get issue status
			String status = extractIssueStatus(issueFields);

			// Add new story into list
			stories.save(new Story.Builder().epic(epic).status(Status.valueOf(status.toUpperCase())).storyPoints(sp)
					.build());
		}));

		return stories;
	}

	/**
	 * Import stories.
	 *
	 * @param features the features
	 * @param stories  the stories
	 */
	public static void importStories(IFeatureDao<String, Feature> features, final IStoryDao<Story> stories) {
		stories.getAll().forEach(s -> updateFeature(features, s));
	}

	/**
	 * Update feature.
	 *
	 * @param features the features
	 * @param story    the story
	 */
	private static void updateFeature(IFeatureDao<String, Feature> features, final Story story) {
		String epic = story.getEpic().orElse("");
		// Get particular feature object
		Feature feature = features.get(epic)
				.orElse(new Feature.Builder().key("").team("").feature("").status(Status.CREATED).build());
		// Increment estimated story points counter
		feature.incEstimated(story.getStoryPoints().orElse(0));
		// Increment working progress counters
		story.getStatus().orElse(Status.INACTIVE).workProgress().recount(feature, story.getStoryPoints().orElse(0));
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
				.append(Utils.prepareUrl(globalParams.getStoriesQuery())).append("&maxResults=").append(maxResults)
				.append("&fields=").append(fields).append("&startAt=").append(startAt);
		return sb.toString();
	}

	/**
	 * Creates the stories repo.
	 *
	 * @param globalParams the global params
	 * @return the i story dao
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static IStoryDao<Story> createStoriesRepo(GlobalParams globalParams)
			throws IOException, InterruptedException {

		// Set initials for stories gathering
		int startAt = 0;
		int maxResults = 1000;
		int totalCount = 0;

		// Create empty stories list
		IStoryDao<Story> stories = new StoryDao();

		do {
			// Get json response
			String jsonStories = IssueStrategy.STORIES.askIssueTracker(globalParams, startAt, maxResults);

			// Extract stories from json
			stories.saveAll(Stories.extractStories(jsonStories).getAll());

			// Get json node "total"
			JsonNode total = new ObjectMapper().readTree(jsonStories).get("total");

			// Get total issues count
			if (total != null && total.isInt())
				totalCount = total.intValue();

			// Increment starting position for json issues pagination
			startAt += maxResults;

		} while (startAt < totalCount);

		return stories;
	}
}
