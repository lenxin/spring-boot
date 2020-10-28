package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.WebApplicationType;

/**
 * General condition used with all servlet session configuration classes.
 *




 */
class ServletSessionCondition extends AbstractSessionCondition {

	ServletSessionCondition() {
		super(WebApplicationType.SERVLET);
	}

}
