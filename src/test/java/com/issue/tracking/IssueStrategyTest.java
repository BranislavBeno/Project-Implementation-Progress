/**
 * 
 */
package com.issue.tracking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.contract.IFeatureDao;
import com.issue.contract.IStoryDao;
import com.issue.model.Feature;
import com.issue.model.Story;
import com.issue.utils.Features;
import com.issue.utils.Stories;
import com.issue.utils.Utils;

/**
 * The Class IssueStrategyTest.
 *
 * @author branislav.beno
 */
class IssueStrategyTest {

	/**
	 * Test negative features json with non existing properties file.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testNegativeFeaturesJsonWithNonExistingPropertiesFile() throws InterruptedException, IOException {
		// Test non existing properties file
		assertThrows(FileNotFoundException.class,
				() -> Utils.provideGlobalParams("src/test/resources/application.properties"));
	}

	/**
	 * Test negative features json with false properties.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testNegativeFeaturesJsonWithFalseProperties() throws InterruptedException, IOException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		// Get expected output
		String expected = Utils.readFileContent("src/test/resources/placeholder.json");

		// Get features json
		String jsonString = IssueStrategy.FEATURES.askIssueTracker(globalParams, null, 0, 1000);

		assertThat(jsonString).isEqualTo(expected);
	}

	/**
	 * Test negative features json with false authentication.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testNegativeFeaturesJsonWithFalseAuthentication() throws InterruptedException, IOException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative2_application.properties");

		// Get features json
		String jsonString = IssueStrategy.FEATURES.askIssueTracker(globalParams, "", 0, 1000);

		assertThat(jsonString).isEqualTo("{\"authenticated\":true}");
	}

	/**
	 * Test positive features json.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testPositiveFeaturesJson() throws InterruptedException, IOException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Set user name for issue tracker authentication
		globalParams.setUsername("postman");

		// Set password for issue tracker authentication
		globalParams.setPassword("password");

		// Get features json
		String jsonString = IssueStrategy.FEATURES.askIssueTracker(globalParams, 0, 1000);
		assertThat(jsonString).isNotNull();

		IFeatureDao<String, Feature> featuresRepo = Features.createFeaturesRepo(globalParams);
		assertThat(featuresRepo.size()).isEqualTo(0);
	}

	/**
	 * Test positive stories json.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testPositiveStoriesJson() throws InterruptedException, IOException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Set user name for issue tracker authentication
		globalParams.setUsername("postman");

		// Set password for issue tracker authentication
		globalParams.setPassword("password");

		// Get features json
		String jsonString = IssueStrategy.STORIES.askIssueTracker(globalParams, 0, 1000);
		assertThat(jsonString).isNotNull();

		IStoryDao<Story> storiesRepo = Stories.createStoriesRepo(globalParams);
		assertThat(storiesRepo.size()).isEqualTo(0);
	}
}
