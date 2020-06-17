package com.issue.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.contract.IFeatureDao;
import com.issue.model.Feature;

/**
 * The Class Send2DB.
 */
public class Send2DB {

	/** The logger. */
	static Logger logger = LogManager.getLogger(Send2DB.class);

	/** The feature list. */
	private List<IFeatureDao<String, Feature>> featureList;

	/** The global parameters. */
	private GlobalParams globalParams;

	/**
	 * Instantiates a new send 2 DB.
	 *
	 * @param globalParams the global params
	 * @param featureList the feature list
	 */
	public Send2DB(GlobalParams globalParams, List<IFeatureDao<String, Feature>> featureList) {
		this.globalParams = Optional.ofNullable(globalParams).orElseThrow(IllegalArgumentException::new);
		this.featureList = featureList;
	}

	/**
	 * Send features 2 DB.
	 *
	 * @param conn the conn
	 */
	private void sendFeatures2DB(Connection conn) {
		// Initialize counter
		int counter = 0;
		for (IFeatureDao<String, Feature> features : featureList) {
			// Create new features's database repository object
			Features4DB features4Db = new Features4DB(conn);

			// Send features repository to data base
			features4Db.save(features, ++counter);
		}
	}

	/**
	 * Send progress 2 DB.
	 */
	public void sendProgress2DB() {

		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			// Send teams repository to data base
			if (featureList != null)
				sendFeatures2DB(conn);
			else
				logger.error("Feature data are null, hence no team data send to database.");

		} catch (SQLException e) {
			logger.error("Sending data to database failed!");
			logger.error("Check whether proper connection parameters are provided or database is connected.");
		}
	}
}
