package org.springframework.boot.docs.jersey;

import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.stereotype.Component;

/**
 * Example configuration for a Jersey {@link ResourceConfig} configured to use
 * {@link HttpServletResponse#setStatus(int)} rather than
 * {@link HttpServletResponse#sendError(int)}.
 *

 */
public class JerseySetStatusOverSendErrorExample {

	// tag::resource-config[]
	@Component
	public class JerseyConfig extends ResourceConfig {

		public JerseyConfig() {
			register(Endpoint.class);
			setProperties(Collections.singletonMap("jersey.config.server.response.setStatusOverSendError", true));
		}

	}
	// end::resource-config[]

	static class Endpoint {

	}

}
