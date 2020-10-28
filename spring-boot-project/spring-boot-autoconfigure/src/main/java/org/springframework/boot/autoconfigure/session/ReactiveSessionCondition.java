package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.WebApplicationType;

/**
 * General condition used with all reactive session configuration classes.
 *

 */
class ReactiveSessionCondition extends AbstractSessionCondition {

	ReactiveSessionCondition() {
		super(WebApplicationType.REACTIVE);
	}

}
