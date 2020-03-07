/**
 * 
 */
package com.issue.output;

import java.util.Optional;

import com.issue.contract.Feature;
import com.issue.utils.Features;
import com.issue.utils.Utils;

/**
 * The Class Feature2Html.
 *
 * @author benito
 */
public class Feature2Html {

	/** The Constant LINE_BEGIN. */
	private static final String LINE_BEGIN = "     <tr>\n";

	/** The Constant LINE_END. */
	private static final String LINE_END = "     </tr>\n";

	/** The Constant GRAY_CELL_CONTENT_BEGIN. */
	private static final String GRAY_CELL_CONTENT_BEGIN = "      <th>";

	/** The Constant GRAY_CELL_CONTENT_END. */
	private static final String GRAY_CELL_CONTENT_END = "</th>\n";

	/** The Constant CELL_CONTENT_BEGIN. */
	private static final String CELL_CONTENT_BEGIN = "      <td style=\"text-align: center;\">";

	/** The Constant CELL_CONTENT_END. */
	private static final String CELL_CONTENT_END = "</td>\n";

	/** The feature. */
	Feature feature;

	/**
	 * Instantiates a new feature 2 html.
	 *
	 * @param feature the feature
	 */
	public Feature2Html(Feature feature) {
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
		// line begin
		sb.append(LINE_BEGIN);
		// team
		sb.append(GRAY_CELL_CONTENT_BEGIN + Utils.replaceSpecialCharacters(feature.getTeam()) + GRAY_CELL_CONTENT_END);
		// feature
		sb.append(GRAY_CELL_CONTENT_BEGIN
				+ Utils.replaceSpecialCharacters(Optional.ofNullable(feature.getFeatureSummary()).orElse("n/a"))
				+ GRAY_CELL_CONTENT_END);
		// estimated
		sb.append(CELL_CONTENT_BEGIN + Features.printDefault(feature.getEstimated()) + CELL_CONTENT_END);
		// opened
		sb.append(CELL_CONTENT_BEGIN + Features.printDefault(feature.getOpened()) + CELL_CONTENT_END);
		// inProgress
		sb.append(CELL_CONTENT_BEGIN + Features.printDefault(feature.getInProgress()) + CELL_CONTENT_END);
		// closed
		sb.append(CELL_CONTENT_BEGIN + Features.printDefault(feature.getClosed()) + CELL_CONTENT_END);
		// done
		sb.append(CELL_CONTENT_BEGIN + Features.printDefault(feature.getDone()) + CELL_CONTENT_END);
		// line end
		sb.append(LINE_END);
		return sb.toString();
	}
}
