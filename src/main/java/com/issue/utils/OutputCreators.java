/**
 * 
 */
package com.issue.utils;

import java.io.IOException;

import com.issue.configuration.GlobalParams;
import com.issue.contract.Dao2Output;
import com.issue.contract.IFeatureDao;
import com.issue.model.Feature;
import com.issue.output.FeatureDao2Csv;
import com.issue.output.FeatureDao2Html;
import com.issue.output.FeatureDao2Xlsx;
import com.issue.output.OutputCreator;

/**
 * The Class Utils.
 *
 * @author branislav.beno
 */
public class OutputCreators {

	/**
	 * Utility classes should not have public constructors.
	 */
	private OutputCreators() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the html output. Utils
	 * 
	 * @param features     the features
	 * @param globalParams the global params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void createHtmlOutput(IFeatureDao<String, Feature> features, GlobalParams globalParams)
			throws IOException {
		// print features to HTML
		OutputCreator outputCreator = new OutputCreator(new FeatureDao2Html(features, globalParams));
		outputCreator.createOutputFile();
	}

	/**
	 * Creates the csv output.
	 *
	 * @param features     the features
	 * @param globalParams the global params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void createCsvOutput(IFeatureDao<String, Feature> features, GlobalParams globalParams)
			throws IOException {
		// print features to CSV
		OutputCreator outputCreator = new OutputCreator(new FeatureDao2Csv(features, globalParams));
		outputCreator.createOutputFile();
	}

	/**
	 * Creates the xlsx output.
	 *
	 * @param features     the features
	 * @param globalParams the global params
	 */
	public static Dao2Output createXlsxOutput(IFeatureDao<String, Feature> features, GlobalParams globalParams) {
		// print features to XLSX
		Dao2Output xlsxOutput = new FeatureDao2Xlsx(features, globalParams);
		xlsxOutput.provideContent();

		return xlsxOutput;
	}
}
