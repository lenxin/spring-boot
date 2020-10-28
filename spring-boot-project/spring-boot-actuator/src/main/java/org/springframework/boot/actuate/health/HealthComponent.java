package org.springframework.boot.actuate.health;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * An component that contributes data to results returned from the {@link HealthEndpoint}.
 *

 * @since 2.2.0
 * @see Health
 * @see CompositeHealth
 */
public abstract class HealthComponent {

	HealthComponent() {
	}

	/**
	 * Return the status of the component.
	 * @return the component status
	 */
	@JsonUnwrapped
	public abstract Status getStatus();

}
