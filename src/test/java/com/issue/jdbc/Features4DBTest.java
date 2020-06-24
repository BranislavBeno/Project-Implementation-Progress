package com.issue.jdbc;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.issue.contract.IFeatureDao;
import com.issue.dao.FeatureDao;
import com.issue.enums.Status;
import com.issue.model.Feature;

class Features4DBTest {

	/** The mocked connection. */
	@Mock
	private Connection mockedConnection;

	/** The mocked statement. */
	@Mock
	private Statement mockedStatement;

	/** The mocked prepared statement. */
	@Mock
	private PreparedStatement mockedPreparedStatement;

	/** The mocked result set. */
	@Mock
	private ResultSet mockedResultSet;

	private IFeatureDao<String, Feature> features;

	/** The DAO. */
	private Features4DB dao;

	@BeforeEach
	void init() throws SQLException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Test whether no established connection to database raises IllegalArgumentException")
	void testNegativeNoConnectionEstablished() throws SQLException {
		assertThrows(IllegalArgumentException.class, () -> new Features4DB(null));
	}

	@Test
	@DisplayName("Test whether non existing prepared statement for database row insertion will be properly handled")
	void testNegativeNonExistingPreparedStatementHenceNoNewTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(false);

		// Create new sprint repository
		dao = new Features4DB(mockedConnection);
		dao.save(features, 0);

		Mockito.verify(mockedStatement, Mockito.times(2)).executeUpdate(Mockito.anyString());
	}

	@Test
	@DisplayName("Test whether non existing prepared statement for database row update will be properly handled")
	void testNegativeNonExistingPreparedStatementHenceNoExistingTableRowUpdate() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(true);

		// Create new sprint repository
		dao = new Features4DB(mockedConnection);
		dao.save(features, 0);

		Mockito.verify(mockedStatement, Mockito.times(2)).executeUpdate(Mockito.anyString());
	}

	@Test
	@DisplayName("Test whether database new row insertion will be successfull")
	void testPositiveNewTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(false);

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(new int[] { 1 });

		// Create new feature
		Feature feature = new Feature.Builder().key("ISSUE-1").team("Banana team").feature("Feature 1")
				.status(Status.OPEN).build();

		// Initialize features
		features = new FeatureDao();

		// Add one new created feature
		features.save(feature);

		// Create new sprint repository
		dao = new Features4DB(mockedConnection);
		dao.save(features, 0);

		Mockito.verify(mockedPreparedStatement).executeBatch();
	}

	@Test
	@DisplayName("Test whether database existing row update will be successfull")
	void testPositiveExistingTableRowUpdate() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(true);

		// Create new sprint
		Feature feature = new Feature.Builder().key("ISSUE-1").team("Banana team").feature("Feature 1")
				.status(Status.OPEN).build();

		Mockito.when(mockedResultSet.getString(1)).thenReturn(feature.getTeam());

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(null);

		// Initialize features
		features = new FeatureDao();

		// Add one new created feature
		features.save(new Feature.Builder().key("ISSUE-1").team("Banana team").feature("Feature 1").status(Status.OPEN)
				.estimated(3).closed(1).build());

		// Create new sprint repository
		dao = new Features4DB(mockedConnection);
		dao.save(features, 0);

		Mockito.verify(mockedPreparedStatement).executeBatch();
	}
}
