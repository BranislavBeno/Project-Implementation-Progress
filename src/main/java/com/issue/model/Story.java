package com.issue.model;

import java.util.Optional;

import com.issue.enums.Status;

/**
 * The Class Story.
 */
public class Story{

	/** The epic. */
	private String epic;

	/** The status. */
	private Status status;

	/** The story points. */
	private Integer storyPoints;

	/**
	 * Instantiates a new story.
	 *
	 * @param builder the builder
	 */
	public Story(Builder builder) {
		this.epic = Optional.ofNullable(builder.epic).orElseThrow();
		this.status = Optional.ofNullable(builder.status).orElseThrow();
		this.storyPoints = builder.storyPoints;
	}

	/**
	 * Gets the epic.
	 *
	 * @return the epic
	 */
	public Optional<String> getEpic() {
		return Optional.ofNullable(epic);
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Optional<Status> getStatus() {
		return Optional.ofNullable(status);
	}

	/**
	 * Gets the story points.
	 *
	 * @return the story points
	 */
	public Optional<Integer> getStoryPoints() {
		return Optional.ofNullable(storyPoints);
	}

	/**
	 * The Class Builder.
	 */
	public static class Builder {

		/** The epic. */
		private String epic;

		/** The status. */
		private Status status;

		/** The story points. */
		private Integer storyPoints = null;

		/**
		 * Epic.
		 *
		 * @param theEpic the the epic
		 * @return the builder
		 */
		public Builder epic(String theEpic) {
			this.epic = theEpic;
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
		 * Story points.
		 *
		 * @param theStoryPoints the the story points
		 * @return the builder
		 */
		public Builder storyPoints(int theStoryPoints) {
			this.storyPoints = theStoryPoints;
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the story
		 */
		public Story build() {
			return new Story(this);
		}
	}
}