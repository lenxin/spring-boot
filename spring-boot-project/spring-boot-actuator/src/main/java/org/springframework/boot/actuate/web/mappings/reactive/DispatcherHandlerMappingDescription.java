package org.springframework.boot.actuate.web.mappings.reactive;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * A description of a mapping known to a {@link DispatcherServlet}.
 *

 * @since 2.0.0
 */
public class DispatcherHandlerMappingDescription {

	private final String predicate;

	private final String handler;

	private final DispatcherHandlerMappingDetails details;

	DispatcherHandlerMappingDescription(String predicate, String handler, DispatcherHandlerMappingDetails details) {
		this.predicate = predicate;
		this.handler = handler;
		this.details = details;
	}

	public String getHandler() {
		return this.handler;
	}

	public String getPredicate() {
		return this.predicate;
	}

	public DispatcherHandlerMappingDetails getDetails() {
		return this.details;
	}

}
