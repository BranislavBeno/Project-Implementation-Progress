package com.issue.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.issue.contract.IFeatureDao;
import com.issue.enums.Status;
import com.issue.model.Feature;
import com.issue.utils.Features;

/**
 * The Class FeatureDao.
 *
 * @author branislav.beno
 */
public class FeatureDao implements IFeatureDao<String, Feature> {

	/** The features. */
	private Map<String, Feature> features = new HashMap<>();

	/**
	 * Gets the feature.
	 *
	 * @param key the key
	 * @return the optional
	 */
	@Override
	public Optional<Feature> get(String key) {
		return Optional.ofNullable(features.get(key));
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public Map<String, Feature> getAll() {
		return features;
	}

	/**
	 * Save.
	 *
	 * @param theFeature the the feature
	 */
	@Override
	public void save(Feature theFeature) {
		features.put(theFeature.getKey(), theFeature);
	}

	/**
	 * Save all.
	 *
	 * @param map the map
	 */
	@Override
	public void saveAll(Map<String, Feature> map) {
		features.putAll(map);
	}

	/**
	 * Update.
	 *
	 * @param theFeature the the feature
	 * @param params     the params
	 */
	@Override
	public void update(Feature theFeature, String[] params) {
		// Parse inputs
		params = Arrays.copyOf(params, 7);
		// Set team
		theFeature.setTeam(Objects.requireNonNullElse(params[0], "NON_MEDICAL"));
		// Set summary
		theFeature.setFeatureSummary(Objects.requireNonNullElse(params[1], "n/a"));
		// Set status
		theFeature.setStatus(updateStatus(theFeature.getStatus(), Objects.requireNonNullElse(params[2], "OPEN")));
		// Set estimated
		theFeature.setEstimated(updateInt(Objects.requireNonNullElse(params[3], "n/a")));
		// Set opened
		theFeature.setOpened(updateInt(Objects.requireNonNullElse(params[4], "n/a")));
		// Set inProgress
		theFeature.setInProgress(updateInt(Objects.requireNonNullElse(params[5], "n/a")));
		// Set closed
		theFeature.setClosed(updateInt(Objects.requireNonNullElse(params[6], "n/a")));
		// Add feature
		features.put(theFeature.getKey(), theFeature);
	}

	/**
	 * Update int.
	 *
	 * @param value the value
	 * @return the integer
	 */
	private Integer updateInt(String value) {
		if (Features.isNumber(value)) {
			return Integer.parseInt(value);
		}
		return null;
	}

	/**
	 * Update status.
	 *
	 * @param prevStatus the prev status
	 * @param value      the value
	 * @return the status
	 */
	private Status updateStatus(Status prevStatus, String value) {
		// all enum items are uppercase
		String status = value.toUpperCase();
		// if enum contains value return related iten
		if (Features.statusContains(status))
			return Status.valueOf(status);
		return prevStatus;
	}

	/**
	 * Delete.
	 *
	 * @param theFeature the the feature
	 */
	@Override
	public void delete(Feature theFeature) {
		features.remove(theFeature.getKey(), theFeature);
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	@Override
	public int size() {
		return features.size();
	}
}
