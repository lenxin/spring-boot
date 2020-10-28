package org.springframework.boot.actuate.availability;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;

/**
 * A {@link HealthIndicator} that checks the {@link LivenessState} of the application.
 *

 * @since 2.3.0
 */
public class LivenessStateHealthIndicator extends AvailabilityStateHealthIndicator {

	public LivenessStateHealthIndicator(ApplicationAvailability availability) {
		super(availability, LivenessState.class, (statusMappings) -> {
			statusMappings.add(LivenessState.CORRECT, Status.UP);
			statusMappings.add(LivenessState.BROKEN, Status.DOWN);
		});
	}

	@Override
	protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
		return applicationAvailability.getLivenessState();
	}

}
