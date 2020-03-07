package com.issue.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.issue.enums.Status;
import com.issue.model.Feature;

/**
 * The Class FeatureDaoTest.
 *
 * @author branislav.beno
 */
class FeatureDaoTest {

	/** The feature dao. */
	private static FeatureDao featureDao;

	/**
	 * Sets the up user dao instance.
	 */
	@BeforeEach
	public void setUpUserDaoInstance() {
		featureDao = new FeatureDao();

		featureDao.save(new Feature.Builder().team("TeamOne").feature("Feature 1").key("ISSUE-1")
				.status(Status.OPEN).estimated(0).opened(0).build());
		featureDao.save(new Feature.Builder().team("TeamTwo").feature("Feature 2").key("ISSUE-2")
				.status(Status.ACTIVE).estimated(52).opened(6).inProgress(3).closed(43).build());
		featureDao.save(new Feature.Builder().team("TeamThree").feature("Feature 3").key("ISSUE-3")
				.status(Status.INACTIVE).estimated(6).inProgress(3).closed(3).build());
	}

	/**
	 * Test feature dao is instance of map.
	 */
	@Test
	public void testFeatureDaoIsInstanceOfMap() {
		assertThat(featureDao.getAll()).isInstanceOf(Map.class);
	}

	/**
	 * Test feature dao update.
	 */
	@Test
	public void testFeatureDaoUpdate() {
		Feature feature = new Feature.Builder().team("TeamThree").feature("Feature 4").key("ISSUE-1")
				.status(Status.OPEN).build();
		featureDao.update(feature, new String[] { "TeamFour", "Feature 44", "Inactive" });

		assertThat(featureDao.get("ISSUE-1").get().getTeam()).isEqualTo("TeamFour");
		assertThat(featureDao.get("ISSUE-1").get().getFeatureSummary()).isEqualTo("Feature 44");
		assertThat(featureDao.get("ISSUE-1").get().getStatus().name()).isEqualTo("INACTIVE");
	}

	/**
	 * Test feature dao full update.
	 */
	@Test
	public void testFeatureDaoFullUpdate() {
		Feature feature = new Feature.Builder().team("TeamThree").feature("Feature 4").key("ISSUE-1")
				.status(Status.INACTIVE).build();
		featureDao.update(feature, new String[] { "TeamFive", "Feature 444", "Activ", "0", "0", "0", "0" });

		assertThat(featureDao.get("ISSUE-1").get().getTeam()).isEqualTo("TeamFive");
		assertThat(featureDao.get("ISSUE-1").get().getStatus().compareTo(Status.INACTIVE)).isEqualTo(0);
		assertThat(featureDao.get("ISSUE-1").get().getDone()).isEqualTo(0.0);
	}

	/**
	 * Test feature dao save.
	 */
	@Test
	public void testFeatureDaoSave() {
		Feature feature = new Feature.Builder().team("TeamThree").feature("Feature 4").key("ISSUE-4")
				.status(Status.ACTIVE).build();
		featureDao.save(feature);

		assertThat(featureDao.get("ISSUE-4").get().getTeam()).isEqualTo("TeamThree");
		assertThat(featureDao.get("ISSUE-4").get().getFeatureSummary()).isEqualTo("Feature 4");
		assertThat(featureDao.get("ISSUE-4").get().getKey()).isEqualTo("ISSUE-4");
		assertThat(featureDao.get("ISSUE-4").get().getStatus().name()).isEqualTo("ACTIVE");
	}

	/**
	 * Test feature dao delete.
	 */
	@Test
	public void testFeatureDaoDelete() {
		featureDao.delete(featureDao.get("ISSUE-1").orElseGet(() -> new Feature.Builder().build()));

		assertThat(featureDao.getAll().size()).isEqualTo(2);
	}

	/**
	 * Test feature dao delete not containing.
	 */
	@Test
	public void testFeatureDaoDeleteNotContaining() {
		Feature feature = new Feature.Builder().team("TeamTwo").feature("Feature 1").key("ISSUE-1")
				.status(Status.OPEN).estimated(0).opened(0).build();
		featureDao.delete(feature);
		assertThat(featureDao.getAll().size()).isEqualTo(3);
	}

	@Test
	public void testFeatureDaoSaveAll() {
		featureDao.saveAll(featureDao.getAll());
		assertThat(featureDao.size()).isEqualTo(3);
	}
}
