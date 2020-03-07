package com.issue.contract;

import java.util.Collection;
import java.util.List;

/**
 * The Interface IStoryDao.
 *
 * @param <T> the generic type
 */
public interface IStoryDao<T> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<T> getAll();

	/**
	 * Save.
	 *
	 * @param t the t
	 */
	void save(T t);

	/**
	 * Save all.
	 *
	 * @param collection the collection
	 */
	void saveAll(Collection<T> collection);

	/**
	 * Size.
	 *
	 * @return the int
	 */
	int size();
}
