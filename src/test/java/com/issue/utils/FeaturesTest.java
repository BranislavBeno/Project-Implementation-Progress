/**
 * 
 */
package com.issue.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;

/**
 * The Class FeaturesTest.
 *
 * @author benito
 */
class FeaturesTest {

	/**
	 * Test features private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testFeaturesPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<Features> clazz = Features.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test negative features json with no connection.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testNegativeFeaturesJsonWithNoConnection() throws InterruptedException, IOException {
		// Provide global parameters
		GlobalParams globalParams = Utils.provideGlobalParams("src/test/resources/test_real_application.properties");

		// Get features
		assertThrows(ConnectException.class, () -> Features.createFeaturesRepo(globalParams, "issuetype = Feature"));
	}
}
