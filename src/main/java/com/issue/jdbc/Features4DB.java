package com.issue.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.contract.IFeatureDao;
import com.issue.model.Feature;
import com.issue.utils.Features;

/**
 * The Class Features4DB.
 */
public class Features4DB {

	/** The logger. */
	static Logger logger = LogManager.getLogger(Features4DB.class);

	/** The Constant TABLE_PREFIX. */
	private static final String TABLE_PREFIX = "phase_";

	/** The Constant DOUBLE_7_4_DEFAULT_0. */
	private static final String DOUBLE_7_4_DEFAULT_0 = "DOUBLE(7,4) DEFAULT 0";

	/** The Constant DECIMAL_4_DEFAULT_0. */
	private static final String DECIMAL_4_DEFAULT_0 = "DECIMAL(4) DEFAULT 0";

	/** The Constant VARCHAR_255_DEFAULT_NULL. */
	private static final String VARCHAR_255_DEFAULT_NULL = "VARCHAR(255) DEFAULT NULL";

	/** The Constant BOOLEAN_DEFAULT_FALSE. */
	private static final String BOOLEAN_DEFAULT_FALSE = "BOOLEAN DEFAULT FALSE";

	/** The Constant DATETIME_DEFAULT_NULL. */
	private static final String DATETIME_DEFAULT_NULL = "DATETIME DEFAULT NULL";

	/** The Constant TEAM_NAME_COLUMN. */
	private static final String TEAM_NAME_COLUMN = "team_name";

	/** The Constant ISSUE_ID_COLUMN. */
	private static final String ISSUE_ID_COLUMN = "issue_id";

	/** The Constant ISSUE_NAME_COLUMN. */
	private static final String ISSUE_NAME_COLUMN = "issue_name";

	/** The Constant ESTIMATED_SP_COLUMN. */
	private static final String ESTIMATED_SP_COLUMN = "estimated_sp";

	/** The Constant OPENED_SP_COLUMN. */
	private static final String OPENED_SP_COLUMN = "opened_sp";

	/** The Constant IN_PROGRESS_SP_COLUMN. */
	private static final String IN_PROGRESS_SP_COLUMN = "in_progress_sp";

	/** The Constant CLOSED_SP_COLUMN. */
	private static final String CLOSED_SP_COLUMN = "closed_sp";

	/** The Constant DONE_SP_COLUMN. */
	private static final String DONE_SP_COLUMN = "done_sp";

	/** The Constant FEATURE_SP_COLUMN. */
	private static final String FEATURE_SP_COLUMN = "feature_sp";

	/** The Constant STORIES_DEFINED_COLUMN. */
	private static final String STORIES_DEFINED_COLUMN = "stories_defined";

	/** The Constant UPDATED_COLUMN. */
	private static final String UPDATED_COLUMN = "updated";

	/** The connection. */
	private final Connection connection;

	/** The table. */
	private String table = null;

	/**
	 * Instantiates a new features 4 DB.
	 *
	 * @param connection the connection
	 */
	public Features4DB(final Connection connection) {
		this.connection = Optional.ofNullable(connection).orElseThrow(IllegalArgumentException::new);
	}

	/**
	 * Column 4 creation.
	 *
	 * @param name        the name
	 * @param declaration the declaration
	 * @return the string
	 */
	private static final String column4Creation(final String name, final String declaration) {
		return new StringBuilder("`").append(name).append("` ").append(declaration).append(", ").toString();
	}

	/**
	 * Statement 4 insertion.
	 *
	 * @return the string
	 */
	private String statement4Insertion() {
		StringJoiner sj = new StringJoiner(", ", " (", ") ");
		sj.add(TEAM_NAME_COLUMN);
		sj.add(ISSUE_ID_COLUMN);
		sj.add(ISSUE_NAME_COLUMN);
		sj.add(ESTIMATED_SP_COLUMN);
		sj.add(OPENED_SP_COLUMN);
		sj.add(IN_PROGRESS_SP_COLUMN);
		sj.add(CLOSED_SP_COLUMN);
		sj.add(DONE_SP_COLUMN);
		sj.add(FEATURE_SP_COLUMN);
		sj.add(STORIES_DEFINED_COLUMN);
		sj.add(UPDATED_COLUMN);

		return "INSERT INTO " + table + sj.toString() + "VALUES " + "(?,?,?,?,?,?,?,?,?,?,?)";
	}

	/**
	 * Statement 4 table creation.
	 *
	 * @return the string
	 */
	private String statement4TableDeletion() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(table).append(";");

		return sb.toString();
	}

	/**
	 * Statement 4 table creation.
	 *
	 * @return the string
	 */
	private String statement4TableCreation() {
		StringBuilder sb = new StringBuilder();
		sb
				// .append("DROP TABLE IF EXISTS ").append(table).append("; ")
				.append("CREATE TABLE IF NOT EXISTS ").append(table).append(" ( ")
				.append(column4Creation("id", "INT(11) NOT NULL AUTO_INCREMENT"))
				.append(column4Creation(TEAM_NAME_COLUMN, VARCHAR_255_DEFAULT_NULL))
				.append(column4Creation(ISSUE_ID_COLUMN, VARCHAR_255_DEFAULT_NULL))
				.append(column4Creation(ISSUE_NAME_COLUMN, VARCHAR_255_DEFAULT_NULL))
				.append(column4Creation(ESTIMATED_SP_COLUMN, DECIMAL_4_DEFAULT_0))
				.append(column4Creation(OPENED_SP_COLUMN, DECIMAL_4_DEFAULT_0))
				.append(column4Creation(IN_PROGRESS_SP_COLUMN, DECIMAL_4_DEFAULT_0))
				.append(column4Creation(CLOSED_SP_COLUMN, DECIMAL_4_DEFAULT_0))
				.append(column4Creation(DONE_SP_COLUMN, DOUBLE_7_4_DEFAULT_0))
				.append(column4Creation(FEATURE_SP_COLUMN, DECIMAL_4_DEFAULT_0))
				.append(column4Creation(STORIES_DEFINED_COLUMN, BOOLEAN_DEFAULT_FALSE))
				.append(column4Creation(UPDATED_COLUMN, DATETIME_DEFAULT_NULL)).append("PRIMARY KEY (`id`)) ")
				.append("ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");

		return sb.toString();
	}

	/**
	 * Handle value.
	 *
	 * @param value the value
	 * @return the int
	 */
	private int handleValue(final Integer value) {
		if (value != null) {
			return value;
		}

		return -1;
	}

	/**
	 * Handle value.
	 *
	 * @param value the value
	 * @return the double
	 */
	private double handleValue(final Double value) {
		if (value != null) {
			return value;
		}

		return -1;
	}

	/**
	 * Params 4 insertion.
	 *
	 * @param stmt    the stmt
	 * @param feature the feature
	 * @throws SQLException the SQL exception
	 */
	private void params4Insertion(PreparedStatement stmt, final Feature feature) throws SQLException {
		// Set statement for database query
		stmt.setString(1, feature.getTeam());
		stmt.setString(2, feature.getKey());
		stmt.setString(3, feature.getFeatureSummary());
		stmt.setInt(4, handleValue(feature.getEstimated()));
		stmt.setInt(5, handleValue(feature.getOpened()));
		stmt.setInt(6, handleValue(feature.getInProgress()));
		stmt.setInt(7, handleValue(feature.getClosed()));
		stmt.setDouble(8, handleValue(feature.getRawDone()));
		stmt.setInt(9, handleValue(feature.getStoryPoints()));
		stmt.setBoolean(10, feature.isStoriesDefined());
		stmt.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
		stmt.addBatch();
	}

	/**
	 * Insert DAO.
	 *
	 * @param features the features
	 */
	private void insertDao(final IFeatureDao<String, Feature> features) {
		// Insert a new sprint data
		logger.info("Inserting a new features data into table '{}'.", table);

		// Create statement for data insertion
		String insertDataQuery = statement4Insertion();

		try (PreparedStatement statement = connection.prepareStatement(insertDataQuery);) {
			// Get features
			List<Feature> values = Features.prepareFeaturesList(features);

			// Run through all sorted features
			values.forEach(f -> {
				try {
					// Prepare statement for data insertion
					params4Insertion(statement, f);
					// Execute SQL query for data insertion
					statement.executeBatch();
				} catch (SQLException e) {
					logger.error("Inserting error at table {}", table);
				}
			});

			logger.info("New features data insertion successfull.");

			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				logger.error("Error during rollback");
			}
			logger.error("Data insertion into table '{}' failed!", table);
		}
	}

	/**
	 * Recreates the table.
	 */
	private void recreateTable() {
		// Delete statement for old table deletion
		String deleteTableQuery = statement4TableDeletion();

		// Create statement for table creation
		String createTableQuery = statement4TableCreation();

		// Execute SQL query for table creation
		try (Statement statement = connection.createStatement()) {
			// Execute SQL query for table deletion
			statement.executeUpdate(deleteTableQuery);
			logger.info("Old table '{}' deleted successfully.", table);

			// Execute SQL query for table creation
			statement.executeUpdate(createTableQuery);
			logger.info("New table '{}' created successfully.", table);

		} catch (SQLException e) {
			logger.error("DB table {} creation failed!", table);
		}
	}

	/**
	 * Save.
	 *
	 * @param features the features
	 * @param phaseId  the phase id
	 */
	public void save(final IFeatureDao<String, Feature> features, int phaseId) {
		// Set database table name
		this.table = TABLE_PREFIX + phaseId;

		// Recreate new data base table
		recreateTable();

		// Insert new data records
		insertDao(features);
	}
}
