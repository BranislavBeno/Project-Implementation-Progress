package com.issue.enums;

import com.issue.contract.Progressable;

/**
 * The Enum Status.
 */
public enum Status implements Progressable {

	/** The closed. */
	CLOSED {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.CLOSED;
		}
	},
	/** The resolved. */
	RESOLVED {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.CLOSED;
		}
	},
	/** The active. */
	ACTIVE {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.IN_PROGRESS;
		}
	},
	/** The in progress. */
	IN_PROGRESS {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.IN_PROGRESS;
		}
	},
	/** The in review. */
	IN_REVIEW {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.IN_PROGRESS;
		}
	},
	/** The in test. */
	IN_TEST {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.IN_PROGRESS;
		}
	},
	/** The on hold. */
	ON_HOLD {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.IN_PROGRESS;
		}
	},
	/** The ready for review. */
	READY_FOR_REVIEW {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.IN_PROGRESS;
		}
	},
	/** The ready for sprint review. */
	READY_FOR_SPRINT_REVIEW {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.IN_PROGRESS;
		}
	},
	/** The ready for test. */
	READY_FOR_TEST {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.IN_PROGRESS;
		}
	},
	/** The inactive. */
	INACTIVE {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.N_A;
		}
	},
	/** The rejected. */
	REJECTED {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.N_A;
		}
	},
	/** The created. */
	CREATED {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.OPENED;
		}
	},
	/** The open. */
	OPEN {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.OPENED;
		}
	},
	/** The reopened. */
	REOPENED {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.OPENED;
		}
	},
	/** The approved crb. */
	APPROVED_CRB {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.OPENED;
		}
	},
	/** The ready for crb. */
	READY_FOR_CRB {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.OPENED;
		}
	},
	/** The ready for estimation. */
	READY_FOR_ESTIMATION {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.OPENED;
		}
	},
	/** The ready for refinement. */
	READY_FOR_REFINEMENT {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.OPENED;
		}
	},
	/** The ready. */
	READY {
		@Override
		public ProgressStatus workProgress() {
			return ProgressStatus.OPENED;
		}
	};
}
