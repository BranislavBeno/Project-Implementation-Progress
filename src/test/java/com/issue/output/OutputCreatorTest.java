/**
 * 
 */
package com.issue.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.contract.Dao2Output;
import com.issue.contract.IFeatureDao;
import com.issue.contract.IStoryDao;
import com.issue.model.Feature;
import com.issue.model.Story;
import com.issue.utils.Features;
import com.issue.utils.OutputCreators;
import com.issue.utils.Stories;
import com.issue.utils.Utils;

/**
 * The Class OutputCreatorTest.
 *
 * @author benito
 */
class OutputCreatorTest {

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
		IStoryDao<Story> stories = Stories.extractStories(jsonString);
		return stories;
	}

	/**
	 * Test negative html output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeHtmlOutputFileExists() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		assertThrows(IllegalArgumentException.class,
				() -> OutputCreators.createHtmlOutput(extractFeatures(), globalParams));
	}

	/**
	 * Test positive html output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveHtmlOutputFileExists() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Create HTML output
		OutputCreators.createHtmlOutput(extractFeatures(), globalParams);

		assertThat(Files.exists(Paths.get(globalParams.getOutputFileName4Html()))).isTrue();
	}

	/**
	 * Test negative csv output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeCsvOutputFileExists() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		assertThrows(IllegalArgumentException.class,
				() -> OutputCreators.createCsvOutput(extractFeatures(), globalParams));
	}

	/**
	 * Test positive csv output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveCsvOutputFileExists() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Create CSV output
		OutputCreators.createCsvOutput(extractFeatures(), globalParams);

		assertThat(Files.exists(Paths.get(globalParams.getOutputFileName4Csv()))).isTrue();
	}

	/**
	 * Test negative xlsx output file exists.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeXlsxOutputFileExists() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		assertThrows(IllegalArgumentException.class,
				() -> OutputCreators.createXlsxOutput(extractFeatures(), globalParams));
	}

	/**
	 * Test positive xlsx output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveXlsxOutputFileExists() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Extract features from json
		IFeatureDao<String, Feature> features = extractFeatures();

		// Extract stories from json
		IStoryDao<Story> stories = extractStories();

		// Import stories data into features map
		Stories.importStories(features, stories);
		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(features, globalParams);

		assertThat(Files.exists(Paths.get(xlsxOutput.provideOutputFileName()))).isTrue();

		stories.saveAll(stories.getAll());
		assertThat(stories.size()).isEqualTo(1878);
	}
}
