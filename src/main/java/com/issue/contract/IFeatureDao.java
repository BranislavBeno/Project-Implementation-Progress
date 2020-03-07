package com.issue.contract;

import java.util.Map;
import java.util.Optional;

/**
 * The Interface Dao.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface IFeatureDao<K, V> {

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the optional
	 */
	Optional<V> get(K key);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	Map<K, V> getAll();

	/**
	 * Save.
	 *
	 * @param v the v
	 */
	void save(V v);

	/**
	 * Save all.
	 *
	 * @param map the map
	 */
	void saveAll(Map<K, V> map);

	/**
	 * Update.
	 *
	 * @param v      the v
	 * @param params the params
	 */
	void update(V v, String[] params);

	/**
	 * Delete.
	 *
	 * @param v the v
	 */
	void delete(V v);

	/**
	 * Size.
	 *
	 * @return the int
	 */
	int size();
}
