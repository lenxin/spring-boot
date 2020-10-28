package org.springframework.boot.build.bom.bomr.github;

/**
 * A milestone in a {@link GitHubRepository GitHub repository}.
 *

 */
public class Milestone {

	private final String name;

	private final int number;

	Milestone(String name, int number) {
		this.name = name;
		this.number = number;
	}

	/**
	 * Returns the name of the milestone.
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the number of the milestone.
	 * @return the number
	 */
	public int getNumber() {
		return this.number;
	}

	@Override
	public String toString() {
		return this.name + " (" + this.number + ")";
	}

}
