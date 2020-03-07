/**
 * 
 */
package com.issue.contract;

import com.issue.enums.ProgressStatus;

/**
 * The Interface Progressable.
 *
 * @author benito
 */
public interface Progressable {

	/**
	 * Work progress.
	 *
	 * @return the progress status
	 */
	ProgressStatus workProgress();
}
