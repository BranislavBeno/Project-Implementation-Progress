/**
 * 
 */
package com.issue.tracking;

import java.io.IOException;

import com.issue.configuration.GlobalParams;
import com.issue.contract.Trackable;
import com.issue.utils.Features;
import com.issue.utils.Stories;
import com.issue.utils.Utils;

/**
 * The Enum IssueStrategy.
 *
 * @author branislav.beno
 */
public enum IssueStrategy implements Trackable {

	/** The features. */
	FEATURES {
		@Override
		public String askIssueTracker(GlobalParams globalParams, int startAt, int maxResults)
				throws InterruptedException, IOException {
			String request = Features.createRequestUri(globalParams, startAt, maxResults,
					"key,status," + Stories.STORY_POINTS_FIELD_ID + "," + Features.FEATURE_NAME_FIELD_ID + ","
							+ Features.SCRUM_TEAM_FIELD_ID);

			return Utils.gatherJsonString(globalParams.getUsername(), globalParams.getPassword(), request);
		}
	},

	/** The stories. */
	STORIES {
		@Override
		public String askIssueTracker(GlobalParams globalParams, int startAt, int maxResults)
				throws InterruptedException, IOException {
			String request = Stories.createRequestUri(globalParams, startAt, maxResults,
					"status," + Stories.STORY_POINTS_FIELD_ID + "," + Stories.EPIC_LINK_FIELD_ID);

			return Utils.gatherJsonString(globalParams.getUsername(), globalParams.getPassword(), request);
		}
	};
}
