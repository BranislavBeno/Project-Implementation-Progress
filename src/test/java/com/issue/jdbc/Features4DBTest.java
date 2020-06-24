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

	/*
	 * @Test
	 * 
	 * @DisplayName("Test whether non existing prepared statement for database row insertion will be properly handled"
	 * ) void testNegativeNonExistingPreparedStatementHenceNoNewTableRowCreation()
	 * throws SQLException {
	 * Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
	 * Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(
	 * mockedResultSet); Mockito.when(mockedResultSet.first()).thenReturn(false);
	 * 
	 * // Create new sprint repository dao = new Features4DB(mockedConnection);
	 * dao.save(features, 0);
	 * 
	 * Mockito.verify(mockedStatement).executeUpdate(Mockito.anyString()); }
	 */}
