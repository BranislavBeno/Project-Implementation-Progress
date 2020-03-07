/**
 * 
 */
package com.issue.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

/**
 * The Class OutputCreatorsTest.
 *
 * @author benito
 */
class OutputCreatorsTest {

	/**
	 * Test output creators private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testOutputCreatorsPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<OutputCreators> clazz = OutputCreators.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}
}
