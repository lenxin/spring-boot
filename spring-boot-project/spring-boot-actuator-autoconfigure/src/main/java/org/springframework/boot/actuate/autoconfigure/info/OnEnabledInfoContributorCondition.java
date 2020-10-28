package org.springframework.boot.actuate.autoconfigure.info;

import org.springframework.boot.actuate.autoconfigure.OnEndpointElementCondition;
import org.springframework.context.annotation.Condition;

/**
 * {@link Condition} that checks if an info indicator is enabled.
 *

 */
class OnEnabledInfoContributorCondition extends OnEndpointElementCondition {

	OnEnabledInfoContributorCondition() {
		super("management.info.", ConditionalOnEnabledInfoContributor.class);
	}

}
