package org.springframework.boot.actuate.autoconfigure.health;

import org.springframework.boot.actuate.autoconfigure.OnEndpointElementCondition;
import org.springframework.context.annotation.Condition;

/**
 * {@link Condition} that checks if a health indicator is enabled.
 *

 */
class OnEnabledHealthIndicatorCondition extends OnEndpointElementCondition {

	OnEnabledHealthIndicatorCondition() {
		super("management.health.", ConditionalOnEnabledHealthIndicator.class);
	}

}
