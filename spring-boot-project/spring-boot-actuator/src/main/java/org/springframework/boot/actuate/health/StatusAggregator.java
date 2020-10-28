package org.springframework.boot.actuate.health;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Strategy used to aggregate {@link Status} instances.
 * <p>
 * This is required in order to combine subsystem states expressed through
 * {@link Health#getStatus()} into one state for the entire system.
 *

 * @since 2.2.0
 */
@FunctionalInterface
public interface StatusAggregator {

	/**
	 * Return {@link StatusAggregator} instance using default ordering rules.
	 * @return a {@code StatusAggregator} with default ordering rules.
	 * @since 2.3.0
	 */
	static StatusAggregator getDefault() {
		return SimpleStatusAggregator.INSTANCE;
	}

	/**
	 * Return the aggregate status for the given set of statuses.
	 * @param statuses the statuses to aggregate
	 * @return the aggregate status
	 */
	default Status getAggregateStatus(Status... statuses) {
		return getAggregateStatus(new LinkedHashSet<>(Arrays.asList(statuses)));
	}

	/**
	 * Return the aggregate status for the given set of statuses.
	 * @param statuses the statuses to aggregate
	 * @return the aggregate status
	 */
	Status getAggregateStatus(Set<Status> statuses);

}
