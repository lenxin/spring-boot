package org.springframework.boot.actuate.health;

/**
 * Default implementation of {@link HealthIndicator} that returns {@link Status#UP}.
 *


 * @since 2.2.0
 * @see Status#UP
 */
public class PingHealthIndicator extends AbstractHealthIndicator {

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		builder.up();
	}

}
