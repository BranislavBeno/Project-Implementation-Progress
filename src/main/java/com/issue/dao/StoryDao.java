package com.issue.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.issue.contract.IStoryDao;
import com.issue.model.Story;

/**
 * The Class StoryDao.
 */
public class StoryDao implements IStoryDao<Story> {

	/** The stories. */
	private List<Story> stories = new ArrayList<>();

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Story> getAll() {
		return stories;
	}

	/**
	 * Save.
	 *
	 * @param story the story
	 */
	@Override
	public void save(Story story) {
		stories.add(story);
	}

	/**
	 * Save all.
	 *
	 * @param collection the collection
	 */
	@Override
	public void saveAll(Collection<Story> collection) {
		stories.addAll(collection);
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	@Override
	public int size() {
		return stories.size();
	}
}
