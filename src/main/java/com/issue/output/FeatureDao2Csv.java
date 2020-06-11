/**
 * 
 */
package com.issue.output;

import java.util.List;

import com.issue.contract.Dao2Output;
import com.issue.contract.IFeatureDao;
import com.issue.model.Feature;
import com.issue.utils.Features;

/**
 * The Class FeatureDao2Csv.
 *
 * @author branislav.beno
 */
public class FeatureDao2Csv implements Dao2Output {

	/** The dao. */
	private IFeatureDao<String, Feature> dao;

	/** The output file name. */
	private String outputFileName;

	/**
	 * Instantiates a new feature dao 2 csv.
	 *
	 * @param dao the dao
	 * @param outputFileName the output file name
	 */
	public FeatureDao2Csv(IFeatureDao<String, Feature> dao, String outputFileName) {
		this.dao = dao;
		this.outputFileName = outputFileName;
	}

	/**
	 * Provide content.
	 *
	 * @return the string
	 */
	@Override
	public String provideContent() {
		// prepare list of features
		List<Feature> values = Features.prepareFeaturesList(dao);

		// create empty string builder
		StringBuilder sb = new StringBuilder("sep=;\n");

		// add page header
		sb.append(
				"Team;Epic JIRA link;Feature / Function;Estimated [SPs];Opened [SPs];In Progress [SPs];Closed [SPs];Done[%];Stories points;Stories defined;Epic report\n");

		// add features
		values.forEach(feature -> sb.append(new Feature2Csv(feature).toString()));

		return sb.toString();
	}

	/**
	 * Provide output file name.
	 *
	 * @return the string
	 */
	@Override
	public String provideOutputFileName() {
		return this.outputFileName;
	}

}
