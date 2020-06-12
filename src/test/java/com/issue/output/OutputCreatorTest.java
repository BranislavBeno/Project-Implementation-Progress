/**
 * 
 */
package com.issue.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	private List<IFeatureDao<String, Feature>> extractFeatures() throws IOException {
		// Initialize new repository list
		List<IFeatureDao<String, Feature>> list = new ArrayList<>();
		// Get Json string
		String jsonString = Utils.readFileContent("src/test/resources/features.json");
		// Extract features from json
		IFeatureDao<String, Feature> features = Features.parseFeatures(jsonString);
		// Add features repository into repository list
		list.add(features);
		return list;
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
	 * Test negative html output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeHtmlOutputFileExists() throws IOException, InterruptedException {
		assertThrows(IllegalArgumentException.class,
				() -> OutputCreators.createHtmlOutput(extractFeatures().stream().findFirst().orElseThrow(), null));
	}

	/**
	 * Test positive html output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveHtmlOutputFileExists() throws IOException, InterruptedException {
		// Create HTML output
		OutputCreators.createHtmlOutput(extractFeatures().stream().findFirst().orElseThrow(), "test.htm");

		assertThat(Files.exists(Paths.get("test.htm"))).isTrue();
	}

	/**
	 * Test negative csv output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeCsvOutputFileExists() throws IOException, InterruptedException {
		assertThrows(IllegalArgumentException.class,
				() -> OutputCreators.createCsvOutput(extractFeatures().stream().findFirst().orElseThrow(), null));
	}

	/**
	 * Test positive csv output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveCsvOutputFileExists() throws IOException, InterruptedException {
		// Create CSV output
		OutputCreators.createCsvOutput(extractFeatures().stream().findFirst().orElseThrow(), "test.csv");

		assertThat(Files.exists(Paths.get("test.csv"))).isTrue();
	}

	/**
	 * Test negative xlsx output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
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

		// Get list of phase related features
		List<IFeatureDao<String, Feature>> list = extractFeatures();

		// Extract features from json
		IFeatureDao<String, Feature> features = list.stream().findFirst().orElseThrow();

		// Extract stories from json
		IStoryDao<Story> stories = extractStories();

		// Import stories data into features map
		Stories.importStories(features, stories);

		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(list, globalParams);

		assertThat(Files.exists(Paths.get(xlsxOutput.provideOutputFileName()))).isTrue();

		stories.saveAll(stories.getAll());
		assertThat(stories.size()).isEqualTo(1878);
	}
}
