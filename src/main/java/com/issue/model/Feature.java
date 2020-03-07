package com.issue.model;

import java.util.Optional;

import com.issue.enums.Status;

/**
 * The Class Feature.
 *
 * @author branislav.beno
 */
public class Feature {

	/** The key. */
	private String key;

	/** The team. */
	private String team;

	/** The feature summary. */
	private String featureSummary;

	/** The status. */
	private Status status;

	/** The estimated. */
	private Integer estimated;

	/** The opened. */
	private Integer opened;

	/** The in progress. */
	private Integer inProgress;

	/** The closed. */
	private Integer closed;

	/** The done. */
	private Double done = null;

	/** The raw done. */
	private Double rawDone = null;

	/** The stories defined. */
	private boolean storiesDefined = false;

	/**
	 * Instantiates a new feature.
	 *
	 * @param builder the builder
	 */
	public Feature(Builder builder) {
		this.key = Optional.ofNullable(builder.key).orElseThrow();
		this.team = Optional.ofNullable(builder.team).orElseThrow();
		this.featureSummary = Optional.ofNullable(builder.feature).orElseThrow();
		this.status = Optional.ofNullable(builder.status).orElseThrow();
		this.estimated = builder.estimated;
		this.opened = builder.opened;
		this.inProgress = builder.inProgress;
		this.closed = builder.closed;
		computeDone();
	}

	/**
	 * Gets the team.
	 *
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * Sets the team.
	 *
	 * @param team the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 * Gets the feature summary.
	 *
	 * @return the featureSummary
	 */
	public String getFeatureSummary() {
		return featureSummary;
	}

	/**
	 * Sets the feature summary.
	 *
	 * @param featureSummary the featureSummary to set
	 */
	public void setFeatureSummary(String featureSummary) {
		this.featureSummary = featureSummary;
	}

	/**
	 * Gets the estimated.
	 *
	 * @return the estimated
	 */
	public Integer getEstimated() {
		return estimated;
	}

	/**
	 * Sets the estimated.
	 *
	 * @param estimated the estimated to set
	 */
	public void setEstimated(Integer estimated) {
		this.estimated = estimated;

		// update done percentage
		computeDone();
	}

	/**
	 * Inc estimated.
	 *
	 * @param increment the increment
	 */
	public void incEstimated(int increment) {
		// increment estimated story points counter
		if (this.estimated == null)
			this.estimated = 0;
		this.estimated += increment;

		// update done percentage
		computeDone();

		// for feature is defined at least one story
		this.storiesDefined = true;
	}

	/**
	 * Gets the opened.
	 *
	 * @return the opened
	 */
	public Integer getOpened() {
		return opened;
	}

	/**
	 * Sets the opened.
	 *
	 * @param opened the opened to set
	 */
	public void setOpened(Integer opened) {
		this.opened = opened;
	}

	/**
	 * Inc opened.
	 *
	 * @param increment the increment
	 */
	public void incOpened(int increment) {
		// increment opened story points counter
		if (this.opened == null)
			this.opened = 0;
		this.opened += increment;
	}

	/**
	 * Gets the in progress.
	 *
	 * @return the inProgress
	 */
	public Integer getInProgress() {
		return inProgress;
	}

	/**
	 * Sets the in progress.
	 *
	 * @param inProgress the inProgress to set
	 */
	public void setInProgress(Integer inProgress) {
		this.inProgress = inProgress;
	}

	/**
	 * Inc in progress.
	 *
	 * @param increment the increment
	 */
	public void incInProgress(int increment) {
		// increment in progress story points counter
		if (this.inProgress == null)
			this.inProgress = 0;
		this.inProgress += increment;
	}

	/**
	 * Gets the closed.
	 *
	 * @return the closed
	 */
	public Integer getClosed() {
		return closed;
	}

	/**
	 * Sets the closed.
	 *
	 * @param closed the closed to set
	 */
	public void setClosed(Integer closed) {
		this.closed = closed;

		// update done percentage
		computeDone();
	}

	/**
	 * Inc closed.
	 *
	 * @param increment the increment
	 */
	public void incClosed(int increment) {
		// increment closed story points counter
		if (this.closed == null)
			this.closed = 0;
		this.closed += increment;

		// update done percentage
		computeDone();
	}

	/**
	 * Gets the done.
	 *
	 * @return the done
	 */
	public Double getDone() {
		return done;
	}

	/**
	 * Gets the raw done.
	 *
	 * @return the raw done
	 */
	public Double getRawDone() {
		return rawDone;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Checks if is stories defined.
	 *
	 * @return true, if is stories defined
	 */
	public boolean isStoriesDefined() {
		return storiesDefined;
	}

	/**
	 * Compute done.
	 */
	private void computeDone() {
		if (estimated != null) {
			this.done = 0.;
			if (estimated > 0 && closed != null) {
				rawDone = (double) closed / estimated;
				done = rawDone * 100;
			}
		}
	}

	/**
	 * The Class Builder.
	 */
	public static class Builder {

		/** The key. */
		private String key;

		/** The team. */
		private String team;

		/** The feature. */
		private String feature;

		/** The status. */
		private Status status;

		/** The estimated. */
		private Integer estimated = null;

		/** The opened. */
		private Integer opened = null;

		/** The in progress. */
		private Integer inProgress = null;

		/** The closed. */
		private Integer closed = null;

		/**
		 * Key.
		 *
		 * @param theKey the the key
		 * @return the builder
		 */
		public Builder key(String theKey) {
			this.key = theKey;
			return this;
		}

		/**
		 * Team.
		 *
		 * @param theTeam the the team
		 * @return the builder
		 */
		public Builder team(String theTeam) {
			this.team = theTeam;
			return this;
		}

		/**
		 * Feature.
		 *
		 * @param theFeature the the feature
		 * @return the builder
		 */
		public Builder feature(String theFeature) {
			this.feature = theFeature;
			return this;
		}

		/**
		 * Status.
		 *
		 * @param theStatus the the status
		 * @return the builder
		 */
		public Builder status(Status theStatus) {
			this.status = theStatus;
			return this;
		}

		/**
		 * Estimated.
		 *
		 * @param theEstimated the the estimated
		 * @return the builder
		 */
		public Builder estimated(int theEstimated) {
			this.estimated = theEstimated;
			return this;
		}

		/**
		 * Opened.
		 *
		 * @param theOpened the the opened
		 * @return the builder
		 */
		public Builder opened(int theOpened) {
			this.opened = theOpened;
			return this;
		}

		/**
		 * In progress.
		 *
		 * @param theInProgress the the in progress
		 * @return the builder
		 */
		public Builder inProgress(int theInProgress) {
			this.inProgress = theInProgress;
			return this;
		}

		/**
		 * Closed.
		 *
		 * @param theClosed the the closed
		 * @return the builder
		 */
		public Builder closed(int theClosed) {
			this.closed = theClosed;
			return this;
		}

		/**
		 * Builds the Feature instance.
		 *
		 * @return the feature
		 */
		public Feature build() {
			return new Feature(this);
		}
	}
}