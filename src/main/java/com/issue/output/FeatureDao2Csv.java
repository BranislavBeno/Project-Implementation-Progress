/**
 * 
 */
package com.issue.output;

import java.util.List;

import com.issue.configuration.GlobalParams;
import com.issue.contract.Dao2Output;
import com.issue.contract.Feature;
import com.issue.contract.IFeatureDao;
import com.issue.utils.Features;

/**
 * The Class FeatureDao2Csv.
 *
 * @author branislav.beno
 */
public class FeatureDao2Csv implements Dao2Output {

	/** The dao. */
	private IFeatureDao<String, Feature> dao;

	/** The global params. */
	private GlobalParams globalParams;

	/**
	 * Instantiates a new feature dao 2 csv.
	 *
	 * @param dao          the dao
	 * @param globalParams the global params
	 */
	public FeatureDao2Csv(IFeatureDao<String, Feature> dao, GlobalParams globalParams) {
		this.dao = dao;
		this.globalParams = globalParams;
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
				"Team;Epic JIRA link;Feature / Function;Estimated [SPs];Opened [SPs];In Progress [SPs];Closed [SPs];Done[%];Stories defined;Epic report\n");

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
		return globalParams.getOutputFileName4Csv();
	}

}
