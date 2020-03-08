package com.issue.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.contract.IFeatureDao;
import com.issue.contract.IStoryDao;
import com.issue.model.Feature;
import com.issue.model.Story;
import com.issue.output.FeatureDao2Csv;
import com.issue.output.FeatureDao2Html;

/**
 * The Class UtilsTest.
 */
class UtilsTest {

	/** The global params. */
	private GlobalParams globalParams;

	/** The features. */
	IFeatureDao<String, Feature> features;

	/**
	 * Prepare resources.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	private void prepareResources() throws IOException {
		// Provide features repo
		features = featuresRepo();

		// Provide global parameters
		globalParams = Utils.provideGlobalParams("src/test/resources/test_positive_application.properties");
	}

	/**
	 * Extract features.
	 *
	 * @return the dao
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private IFeatureDao<String, Feature> extractFeatures() throws IOException {
		// Get Json string
		String jsonString = Utils.readFileContent("src/test/resources/features.json");
		// extract features from json
		IFeatureDao<String, Feature> features = Features.extractFeatures(jsonString);
		return features;
	}

	/**
	 * Extract stories.
	 *
	 * @return the i story dao
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private IStoryDao<Story> extractStories() throws IOException {
		// Get Json string
		String jsonString = Utils.readFileContent("src/test/resources/stories.json");
		// extract stories from json
		IStoryDao<Story> stories = Stories.parseStories(jsonString);
		return stories;
	}

	/**
	 * Features repo.
	 *
	 * @return the i feature dao
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private IFeatureDao<String, Feature> featuresRepo() throws IOException {
		// extract features
		IFeatureDao<String, Feature> features = extractFeatures();
		// import stories
		Stories.importStories(features, extractStories());
		return features;
	}

	/**
	 * Test private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<Utils> clazz = Utils.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test html output.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testHtmlOutput() throws IOException, InterruptedException {
		// Get expected output
		String expected = Utils.readFileContent("src/test/resources/basic.htm");

		// Prepare current date
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String currentDate = dateFormatter.format(LocalDate.now());
		// Replace persistently saved date with current
		expected = expected.replaceAll("\\d{4}-\\d{2}-\\d{2}", currentDate);

		assertThat(features.size()).isEqualTo(117);
		assertThat(new FeatureDao2Html(features, globalParams).provideContent()).isEqualTo(expected);
	}

	/**
	 * Test csv output.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testCsvOutput() throws IOException, InterruptedException {
		// Get expected output
		String expected = Utils.readFileContent("src/test/resources/basic.csv");

		assertThat(new FeatureDao2Csv(features, globalParams).provideContent()).isEqualTo(expected);
	}

	/**
	 * Test full stats run with no connection exception.
	 */
	@Test
	void testFullStatsRunWithNoConnectionException() {
		// Start main routine
		assertThrows(ConnectException.class, () -> Utils.runProgress("usr", "passwd"));
	}
}
