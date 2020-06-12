package com.issue.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * The Class ProjectPhaseTest.
 */
class ProjectPhaseTest {

	/**
	 * Test negative project phase object creation with first null parameter.
	 */
	@Test
	void testNegativeProjectPhaseObjectCreationWithFirstNullParameter() {
		assertThrows(IllegalArgumentException.class, () -> new ProjectPhase(null, null));
	}

	/**
	 * Test negative project phase object creation with second null parameter.
	 */
	@Test
	void testNegativeProjectPhaseObjectCreationWithSecondNullParameter() {
		assertThrows(IllegalArgumentException.class, () -> new ProjectPhase("", null));
	}

	/**
	 * Testpositive project phase object creation.
	 */
	@Test
	void testpositiveProjectPhaseObjectCreation() {
		// Create new project phase object
		ProjectPhase projectPhase = new ProjectPhase("", "");

		assertThat(projectPhase.getOutputFileName4Html()).isNull();
		assertThat(projectPhase.getOutputFileName4Csv()).isNull();
	}
}
