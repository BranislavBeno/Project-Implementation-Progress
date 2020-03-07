/**
 * 
 */
package com.issue.enums;

import com.issue.contract.Countable;
import com.issue.contract.Feature;

/**
 * The Enum ProgressStatus.
 *
 * @author benito
 */
public enum ProgressStatus implements Countable {

	/** The closed. */
	CLOSED {
		@Override
		public void recount(Feature feature, final int increment) {
			feature.incClosed(increment);
		}
	},
	/** The in progress. */
	IN_PROGRESS {
		@Override
		public void recount(Feature feature, final int increment) {
			feature.incInProgress(increment);
		}
	},
	/** The opened. */
	OPENED {
		@Override
		public void recount(Feature feature, final int increment) {
			feature.incOpened(increment);
		}
	},
	/** The n a. */
	N_A {
		@Override
		public void recount(Feature feature, final int increment) {
			// Do nothing
		}
	};
}
