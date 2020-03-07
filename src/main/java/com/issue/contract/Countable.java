/**
 * 
 */
package com.issue.contract;

import com.issue.model.Feature;

/**
 * The Interface Countable.
 *
 * @author benito
 */
public interface Countable {

	/**
	 * Recount.
	 *
	 * @param feature   the feature
	 * @param increment the increment
	 */
	void recount(Feature feature, final int increment);
}
