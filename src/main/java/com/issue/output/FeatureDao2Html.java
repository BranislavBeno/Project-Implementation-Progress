/**
 * 
 */
package com.issue.output;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.issue.configuration.GlobalParams;
import com.issue.contract.Dao2Output;
import com.issue.contract.IFeatureDao;
import com.issue.model.Feature;
import com.issue.utils.Features;

/**
 * The Class FeatureDao2Html.
 *
 * @author benito
 */
public class FeatureDao2Html implements Dao2Output {

	/** The Constant AC_LAYOUT_CELL_BEGINNING_TAG_1. */
	private static final String AC_LAYOUT_CELL_BEGINNING_TAG_1 = "  </ac:layout-cell>\n";

	/** The Constant AC_LAYOUT_CELL_BEGINNING_TAG_2. */
	private static final String AC_LAYOUT_CELL_BEGINNING_TAG_2 = "    </ac:layout-cell>\n";

	/** The Constant AC_LAYOUT_CELL_BEGINNING_TAG_3. */
	private static final String AC_LAYOUT_CELL_BEGINNING_TAG_3 = "  <ac:layout-cell>\n";

	/** The Constant AC_LAYOUT_SECTION_BEGINNING_TAG. */
	private static final String AC_LAYOUT_SECTION_BEGINNING_TAG = " </ac:layout-section>\n";

	/** The Constant P_BEGINNING_TAG. */
	private static final String P_BEGINNING_TAG = "   <p class=\"auto-cursor-target\">\n";

	/** The Constant TD_BEGINNING_TAG. */
	private static final String TD_BEGINNING_TAG = "      <td colspan=\"1\">\n";

	/** The Constant BR_ENDING_TAG_1. */
	private static final String BR_ENDING_TAG_1 = "    <br/>\n";

	/** The Constant BR_ENDING_TAG_2. */
	private static final String BR_ENDING_TAG_2 = "     <br/>\n";

	/** The Constant BR_ENDING_TAG_3. */
	private static final String BR_ENDING_TAG_3 = "       <br/>\n";

	/** The Constant P_ENDING_TAG_1. */
	private static final String P_ENDING_TAG_1 = "   </p>\n";

	/** The Constant P_ENDING_TAG_2. */
	private static final String P_ENDING_TAG_2 = "   <p>\n";

	/** The Constant TR_ENDING_TAG. */
	private static final String TR_ENDING_TAG_1 = "     </tr>\n";

	/** The Constant TR_ENDING_TAG_2. */
	private static final String TR_ENDING_TAG_2 = "     <tr>\n";

	/** The Constant TD_ENDING_TAG. */
	private static final String TD_ENDING_TAG = "      </td>\n";

	/** The Constant COL_ENDING_TAG. */
	private static final String COL_ENDING_TAG = "     <col/>\n";

	/** The dao. */
	private IFeatureDao<String, Feature> dao;

	/** The global params. */
	private GlobalParams globalParams;

	/**
	 * Instantiates a new feature dao 2 html.
	 *
	 * @param dao          the dao
	 * @param globalParams the global params
	 */
	public FeatureDao2Html(IFeatureDao<String, Feature> dao, GlobalParams globalParams) {
		this.dao = dao;
		this.globalParams = globalParams;
	}

	/**
	 * Prints the html second table.
	 *
	 * @return the string
	 */
	private String printHtmlSecondTable() {
		// add second table
		return " <ac:layout-section ac:type=\"single\">\n" + AC_LAYOUT_CELL_BEGINNING_TAG_3 + P_BEGINNING_TAG
				+ "    <strong> <br/> </strong>\n" + P_ENDING_TAG_1 + P_ENDING_TAG_2
				+ "    <strong>Change requests list - changes which were not considered in the origin timeplan.</strong>\n"
				+ P_ENDING_TAG_1 + "   <table class=\"wrapped\">\n" + "    <colgroup>\n" + COL_ENDING_TAG
				+ "     <col style=\"width: 217.0px;\"/>\n" + "     <col style=\"width: 792.0px;\"/>\n"
				+ "     <col style=\"width: 117.0px;\"/>\n" + "     <col style=\"width: 99.0px;\"/>\n"
				+ "    </colgroup>\n" + "    <tbody>\n" + TR_ENDING_TAG_2
				+ "      <th colspan=\"1\">Date of decision</th>\n" + "      <th>Feature</th>\n"
				+ "      <th>Description</th>\n" + "      <th>Estimation</th>\n" + "      <th>JIRA ID</th>\n"
				+ TR_ENDING_TAG_1 + TR_ENDING_TAG_2 + TD_BEGINNING_TAG + BR_ENDING_TAG_3 + TD_ENDING_TAG
				+ TD_BEGINNING_TAG + BR_ENDING_TAG_3 + TD_ENDING_TAG + TD_BEGINNING_TAG + BR_ENDING_TAG_3
				+ TD_ENDING_TAG + TD_BEGINNING_TAG + BR_ENDING_TAG_3 + TD_ENDING_TAG + TD_BEGINNING_TAG
				+ BR_ENDING_TAG_3 + TD_ENDING_TAG + TR_ENDING_TAG_1 + "    </tbody>\n" + "   </table>\n"
				+ P_BEGINNING_TAG + BR_ENDING_TAG_1 + P_ENDING_TAG_1 + AC_LAYOUT_CELL_BEGINNING_TAG_1
				+ AC_LAYOUT_SECTION_BEGINNING_TAG;
	}

	/**
	 * Prints the html table footer.
	 *
	 * @return the string
	 */
	private String printHtmlTableFooter() {
		// add table footer
		return "    </tbody>\n" + "   </table>\n" + P_BEGINNING_TAG + BR_ENDING_TAG_1 + P_ENDING_TAG_1
				+ AC_LAYOUT_CELL_BEGINNING_TAG_1 + AC_LAYOUT_SECTION_BEGINNING_TAG;
	}

	/**
	 * Prints the html table header.
	 *
	 * @return the string
	 */
	private String printHtmlTableHeader() {
		// add table header
		return " <ac:layout-section ac:type=\"single\">\n" + AC_LAYOUT_CELL_BEGINNING_TAG_3 + P_ENDING_TAG_2
				+ "    <strong style=\"font-size: 16.0px;\">Implementation Progress (Values in Story Points recorded in Jira)</strong>\n"
				+ P_ENDING_TAG_1 + "   <table class=\"wrapped\">\n" + "    <colgroup>\n" + COL_ENDING_TAG
				+ COL_ENDING_TAG + COL_ENDING_TAG + COL_ENDING_TAG + COL_ENDING_TAG + COL_ENDING_TAG + COL_ENDING_TAG
				+ "    </colgroup>\n" + "    <tbody>\n" + TR_ENDING_TAG_2 + "      <th>\n" + BR_ENDING_TAG_3
				+ "      </th>\n" + "      <th>\n" + BR_ENDING_TAG_3 + "      </th>\n"
				+ "      <th colspan=\"5\" rowspan=\"1\" style=\"text-align: center;\">Story Point View</th>\n"
				+ TR_ENDING_TAG_1 + TR_ENDING_TAG_2 + "      <th style=\"text-align: center;\">Team</th>\n"
				+ "      <th style=\"text-align: center;\">Feature / Function</th>\n"
				+ "      <th style=\"text-align: center;\">Estimated<br/> [SPs]</th>\n"
				+ "      <th style=\"text-align: center;\">Opened<br/> [SPs]</th>\n"
				+ "      <th style=\"text-align: center;\">In Progress<br/> [SPs]</th>\n"
				+ "      <th style=\"text-align: center;\">Closed<br/> [SPs]</th>\n"
				+ "      <th style=\"text-align: center;\">Done<br/> [%]</th>\n" + TR_ENDING_TAG_1;
	}

	/**
	 * Prints the html description section.
	 *
	 * @return the string
	 */
	private String printHtmlDescriptionSection() {
		// add description section
		return " <ac:layout-section ac:type=\"two_equal\">\n" + AC_LAYOUT_CELL_BEGINNING_TAG_3 + P_BEGINNING_TAG
				+ BR_ENDING_TAG_1 + P_ENDING_TAG_1
				+ "   <ac:structured-macro ac:macro-id=\"d45fa9a5-49f4-4ae9-9417-f81f4b86c28c\" ac:name=\"note\" ac:schema-version=\"1\">\n"
				+ "    <ac:rich-text-body>\n"
				+ "     <em> <strong>Following page is administrated by PM and SCRUM Master. In case of any change needed, please contact them directly via email: <a class=\"Xx\" href=\"mailto:Jan.Kall.extern@corpuls.com\">Jan.Kall.extern@corpuls.com</a>, <a href=\"mailto:Branislav.Beno.extern@corpuls.com\">Branislav.Beno.extern@corpuls.com</a> </strong> </em>\n"
				+ "    </ac:rich-text-body>\n" + "   </ac:structured-macro>\n"
				+ "    <p class=\"auto-cursor-target\">\n" + BR_ENDING_TAG_2 + "    </p>\n"
				+ AC_LAYOUT_CELL_BEGINNING_TAG_1 + AC_LAYOUT_CELL_BEGINNING_TAG_3 + P_ENDING_TAG_2 + BR_ENDING_TAG_1
				+ P_ENDING_TAG_1 + AC_LAYOUT_CELL_BEGINNING_TAG_1 + AC_LAYOUT_SECTION_BEGINNING_TAG;
	}

	/**
	 * Prints the html title section.
	 *
	 * @return the string
	 */
	private String printHtmlTitleSection() {
		// prepare current date
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String currentDate = dateFormatter.format(LocalDate.now());
		// add title section
		return " <ac:layout-section ac:type=\"two_right_sidebar\">\n" + "    <ac:layout-cell>\n"
				+ "      <p>Target date: </p>\n"
				+ "      <p>Estimation of features deliverable based on currents state of development and scope increase is: </p>\n"
				+ AC_LAYOUT_CELL_BEGINNING_TAG_2 + "    <ac:layout-cell>\n" + "      <p>As of: <time datetime=\""
				+ currentDate + "\"/> </p>\n" + AC_LAYOUT_CELL_BEGINNING_TAG_2 + AC_LAYOUT_SECTION_BEGINNING_TAG;
	}

	/**
	 * Provide content.
	 *
	 * @return the string
	 */
	@Override
	public String provideContent() {
		// prepare list of features
		List<Feature> values = Features.prepareFilteredFeaturesList(dao);

		// create empty string builder
		StringBuilder sb = new StringBuilder();

		// add page header
		sb.append("<ac:layout>\n");

		// add description section
		sb.append(printHtmlDescriptionSection());

		// add title section
		sb.append(printHtmlTitleSection());

		// add table header
		sb.append(printHtmlTableHeader());

		// add features
		values.forEach(feature -> sb.append(new Feature2Html(feature).toString()));

		// add table footer
		sb.append(printHtmlTableFooter());

		// add second table
		sb.append(printHtmlSecondTable());

		// add page footer
		sb.append("</ac:layout>");

		return sb.toString();
	}

	/**
	 * Provide output file name.
	 *
	 * @return the string
	 */
	@Override
	public String provideOutputFileName() {
		return globalParams.getOutputFileName4Html();
	}
}
