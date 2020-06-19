package com.issue.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.contract.IFeatureDao;
import com.issue.dao.FeatureDao;
import com.issue.enums.Status;
import com.issue.model.Feature;
import com.issue.utils.Utils;

class Send2DBTest {

	private List<IFeatureDao<String, Feature>> prepareFeaturesRepo() {
		// Create empty features repository
		IFeatureDao<String, Feature> features = new FeatureDao();

		// Save one new feature
		features.save(new Feature.Builder().key("ISSUE-1").team("Banana team").feature("Feature 1").status(Status.OPEN)
				.build());

		// Create empty list of feature repositories
		List<IFeatureDao<String, Feature>> featuresList = new ArrayList<>();

		// Add one feature repository into list
		featuresList.add(features);

		return featuresList;
	}

	@Test
	@DisplayName("Test whether no object is created and IllegalArgumentException is raised")
	void testNoObjectCreated() throws SQLException {
		assertThrows(IllegalArgumentException.class, () -> new Send2DB(null, null));
	}

	@Test
	@DisplayName("Test whether object is created but database connection parameters are false")
	void testObjectCreatedButDataBaseConnectionDataAreFalse() throws SQLException, IOException {
		// Prepare parameters for database connection
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		// Create object for database connection
		Send2DB send2DB = new Send2DB(globalParams, null);
		// Send data to database
		send2DB.sendProgress2DB();

		assertThat(send2DB).isNotNull();
	}

	@Test
	@DisplayName("Test whether object is created but data for sending to database are null")
	void testObjectCreatedButDataToSendAreNull() throws SQLException, IOException {
		// Prepare parameters for database connection
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Create object for database connection
		Send2DB send2DB = new Send2DB(globalParams, null);
		// Send data to database
		send2DB.sendProgress2DB();

		assertThat(send2DB).isNotNull();
	}

	@Test
	void testDataAreSentToDB() throws SQLException, IOException {
		// Prepare parameters for database connection
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Prepare features repository
		List<IFeatureDao<String, Feature>> featureList = prepareFeaturesRepo();

		// Create object for database connection
		Send2DB send2DB = new Send2DB(globalParams, featureList);
		// Send data to database
		send2DB.sendProgress2DB();

		assertThat(send2DB).isNotNull();
	}
}
