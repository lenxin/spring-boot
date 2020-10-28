package org.springframework.boot.build.bom.bomr.github;

import java.util.List;

/**
 * Minimal API for interacting with a GitHub repository.
 *

 */
public interface GitHubRepository {

	/**
	 * Opens a new issue with the given title. The given {@code labels} will be applied to
	 * the issue and it will be assigned to the given {@code milestone}.
	 * @param title the title of the issue
	 * @param labels the labels to apply to the issue
	 * @param milestone the milestone to assign the issue to
	 * @return the number of the new issue
	 */
	int openIssue(String title, List<String> labels, Milestone milestone);

	/**
	 * Returns the labels in the repository.
	 * @return the labels
	 */
	List<String> getLabels();

	/**
	 * Returns the milestones in the repository.
	 * @return the milestones
	 */
	List<Milestone> getMilestones();

}
