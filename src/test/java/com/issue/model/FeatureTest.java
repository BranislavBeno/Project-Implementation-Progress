package com.issue.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import com.issue.enums.Status;
import com.issue.output.Feature2Html;

/**
 * The Class FeatureTest.
 *
 * @author branislav.beno
 */
class FeatureTest {

	/**
	 * Test complete empty feature output.
	 */
	@Test
	void testCompleteEmptyFeatureOutput() {
		assertThrows(NoSuchElementException.class, () -> new FeatureImpl.Builder().build());
	}

	/**
	 * Test reasonable empty feature output.
	 */
	@Test
	void testReasonableEmptyFeatureOutput() {
		String expected = "     <tr>\n" + "      <th>TeamOne</th>\n" + "      <th>Feature &lt;1</th>\n"
				+ "      <td style=\"text-align: center;\">n/a</td>\n"
				+ "      <td style=\"text-align: center;\">n/a</td>\n"
				+ "      <td style=\"text-align: center;\">n/a</td>\n"
				+ "      <td style=\"text-align: center;\">n/a</td>\n"
				+ "      <td style=\"text-align: center;\">n/a</td>\n" + "     </tr>\n";

		FeatureImpl feature = new FeatureImpl.Builder().feature("Feature <1").team("TeamOne").key("ISSUE-1")
				.status(Status.OPEN).build();

		assertThat(new Feature2Html(feature).toString()).isEqualTo(expected);
	}

	/**
	 * Test non empty feature output.
	 */
	@Test
	void testNonEmptyFeatureOutput1() {
		String expected = "     <tr>\n" + "      <th>TeamTwo</th>\n" + "      <th>Feature &gt;1</th>\n"
				+ "      <td style=\"text-align: center;\">0</td>\n"
				+ "      <td style=\"text-align: center;\">0</td>\n"
				+ "      <td style=\"text-align: center;\">n/a</td>\n"
				+ "      <td style=\"text-align: center;\">n/a</td>\n"
				+ "      <td style=\"text-align: center;\">0%</td>\n" + "     </tr>\n";

		FeatureImpl feature = new FeatureImpl.Builder().team("TeamTwo").feature("Feature >1").key("ISSUE-1")
				.status(Status.OPEN).estimated(0).opened(0).build();

		assertThat(new Feature2Html(feature).toString()).isEqualTo(expected);
	}

	/**
	 * Test non empty feature output.
	 */
	@Test
	void testNonEmptyFeatureOutput2() {
		String expected = "     <tr>\n" + "      <th>TeamThree</th>\n" + "      <th>Feature &apos;1</th>\n"
				+ "      <td style=\"text-align: center;\">0</td>\n"
				+ "      <td style=\"text-align: center;\">0</td>\n"
				+ "      <td style=\"text-align: center;\">n/a</td>\n"
				+ "      <td style=\"text-align: center;\">0</td>\n"
				+ "      <td style=\"text-align: center;\">0%</td>\n" + "     </tr>\n";

		FeatureImpl feature = new FeatureImpl.Builder().team("TeamThree").feature("Feature '1").key("ISSUE-1")
				.status(Status.OPEN).estimated(0).opened(0).closed(0).build();

		assertThat(new Feature2Html(feature).toString()).isEqualTo(expected);
	}

	/**
	 * Test non empty feature output.
	 */
	@Test
	void testNonEmptyFeatureOutput3() {
		String expected = "     <tr>\n" + "      <th>TeamFour</th>\n" + "      <th>Feature &quot;1</th>\n"
				+ "      <td style=\"text-align: center;\">1</td>\n"
				+ "      <td style=\"text-align: center;\">0</td>\n"
				+ "      <td style=\"text-align: center;\">0</td>\n"
				+ "      <td style=\"text-align: center;\">n/a</td>\n"
				+ "      <td style=\"text-align: center;\">0%</td>\n" + "     </tr>\n";

		FeatureImpl feature = new FeatureImpl.Builder().team("TeamFour").feature("Feature \"1").key("ISSUE-1")
				.status(Status.OPEN).estimated(1).opened(0).inProgress(0).build();

		assertThat(new Feature2Html(feature).toString()).isEqualTo(expected);
	}

	/**
	 * Test full feature output.
	 */
	@Test
	void testFullFeatureOutput() {
		String expected = "     <tr>\n" + "      <th>TeamFive</th>\n" + "      <th>Feature &amp;2</th>\n"
				+ "      <td style=\"text-align: center;\">52</td>\n"
				+ "      <td style=\"text-align: center;\">6</td>\n"
				+ "      <td style=\"text-align: center;\">3</td>\n"
				+ "      <td style=\"text-align: center;\">43</td>\n"
				+ "      <td style=\"text-align: center;\">82%</td>\n" + "     </tr>\n";

		FeatureImpl feature = new FeatureImpl.Builder().team("TeamFive").feature("Feature &2").key("ISSUE-1")
				.status(Status.OPEN).estimated(52).opened(6).inProgress(3).closed(43).build();

		assertThat(new Feature2Html(feature).toString()).isEqualTo(expected);
	}
}
