/**
 * 
 */
package com.issue.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.contract.IFeatureDao;
import com.issue.contract.IStoryDao;
import com.issue.model.Feature;
import com.issue.model.Story;

/**
 * The Class Utils.
 *
 * @author branislav.beno
 */
public class Utils {

	/** The logger. */
	static Logger logger = LogManager.getLogger(Utils.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Provide properties.
	 *
	 * @param propFile the prop file
	 * @return the properties
	 * @throws FileNotFoundException the file not found exception
	 */
	private static Properties provideProperties(final String propFile) throws FileNotFoundException {
		// Create new properties object
		Properties properties = new Properties();
		// Load properties from file
		try {
			properties.load(new FileInputStream(propFile));
		} catch (IOException e) {
			logger.error("Propreties file {} was not found!", propFile);
			throw new FileNotFoundException();
		}

		return properties;
	}

	/**
	 * Provide global params.
	 *
	 * @param propFile the prop file
	 * @return the global params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static GlobalParams provideGlobalParams(final String propFile) throws IOException {
		// Create empty global parameters
		GlobalParams globalParams = GlobalParams.getInstance();

		// Get properties
		Properties props = provideProperties(propFile);

		// Get content from properties
		globalParams.setIssueTrackerUri(props.getProperty("issueTrackerUri"));
		globalParams.setIssueUri(props.getProperty("issueUri"));
		globalParams.setEpicReportUri(props.getProperty("epicReportUri"));
		globalParams.setOutputFileName4Html(props.getProperty("htmlOutputFile"));
		globalParams.setOutputFileName4Csv(props.getProperty("csvOutputFile"));
		globalParams.setOutputFileName4Xlsx(props.getProperty("xlsxOutputFile"));
		globalParams.setFeaturesQuery(props.getProperty("featuresQuery", "issuetype = Feature"));
		globalParams.setStoriesQuery(props.getProperty("storiesQuery", "issuetype = Story"));

		return globalParams;
	}

	/**
	 * Basic auth.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the string
	 */
	private static String basicAuth(final String username, final String password) {
		return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}

	/**
	 * Gather json string.
	 *
	 * @param username the username
	 * @param password the password
	 * @param uri      the uri
	 * @return the string
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static String gatherJsonString(final String username, final String password, final String uri)
			throws IOException, InterruptedException {
		// Create HTTP Client
		HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

		// Create HTTP Request
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).version(HttpClient.Version.HTTP_2)
				.header("Authorization", basicAuth(username, password)).header("Content-Type", "application/json").GET()
				.build();

		try {
			// Get HTTP response
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			// Get response status code
			int responseStatusCode = response.statusCode();

			// Get response body
			if (responseStatusCode == 200) {
				logger.info("Json output exported succesfully.");
				return response.body();
			}

			logger.error("HTTP Error {} occured!", responseStatusCode);
			logger.error("Json output wasn't exported!");

		} catch (ConnectException e) {
			logger.error("Connection timed out: Issue tracker tool not reachable!");
			throw new ConnectException();
		}

		return null;
	}

	/**
	 * Read all.
	 *
	 * @param reader the reader
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String readAll(final Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		return String.valueOf(sb);
	}

	/**
	 * Read file content.
	 *
	 * @param filePath the file path
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String readFileContent(final String filePath) throws IOException {
		String content = null;
		File file = new File(filePath);
		try (Reader fileReader = new FileReader(file)) {
			content = readAll(fileReader);
		}
		return content;
	}

	/**
	 * Replace special characters related to XML or HTML.
	 *
	 * @param text the text
	 * @return the string
	 */
	public static String replaceSpecialCharacters(String text) {
		StringBuilder sb = new StringBuilder();
		for (char c : text.toCharArray()) {
			switch (c) {
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Prepare url.
	 *
	 * @param text the text
	 * @return the string
	 */
	public static String prepareUrl(String text) {
		StringBuilder sb = new StringBuilder();
		for (char c : text.toCharArray()) {
			sb.append("%" + Integer.toHexString((byte) c));
		}
		return sb.toString();
	}

	/**
	 * Run progress.
	 *
	 * @param user   the user
	 * @param passwd the passwd
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static void runProgress(final String user, final String passwd) throws IOException, InterruptedException {
		// Start processing.
		logger.info("Processing started.");

		// Kick off progress thread. // Provide global parameters
		GlobalParams globalParams = Utils.provideGlobalParams("application.properties");

		// Set user name for issue tracker authentication
		globalParams.setUsername(user);

		// Set password for issue tracker authentication
		globalParams.setPassword(passwd);

		// Create features repository
		IFeatureDao<String, Feature> features = Features.createFeaturesRepo(globalParams);
		logger.info("{} features processed.", features.size());

		// Create stories repository
		IStoryDao<Story> stories = Stories.createStoriesRepo(globalParams);
		logger.info("{} stories processed.", stories.size());

		// Import stories data into features map
		Stories.importStories(features, stories);

		// Create HTML output
		try {
			OutputCreators.createHtmlOutput(features, globalParams);
			logger.info("File with HTML content created successfully.");
		} catch (IllegalArgumentException e) {
			logger.warn("No file name for HTML output in properties file defined.");
		}

		// Create CSV output
		try {
			OutputCreators.createCsvOutput(features, globalParams);
			logger.info("File with CSV content created successfully.");
		} catch (IllegalArgumentException e) {
			logger.warn("No file name for CSV output in properties file defined.");
		}

		// Create XLSX output
		try {
			OutputCreators.createXlsxOutput(features, globalParams);
			logger.info("File with XLSX content created successfully.");
		} catch (IllegalArgumentException e) {
			logger.warn("No file name for XLSX output in properties file defined.");
		}

		// Processing finished.
		logger.info("Processing finished.");
	}
}
