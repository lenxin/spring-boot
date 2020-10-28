package org.springframework.boot.actuate.web.mappings.servlet;

import java.util.Collection;

import javax.servlet.ServletRegistration;

/**
 * A mapping description derived from a {@link ServletRegistration}.
 *

 * @since 2.0.0
 */
public class ServletRegistrationMappingDescription extends RegistrationMappingDescription<ServletRegistration> {

	/**
	 * Creates a new {@code ServletRegistrationMappingDescription} derived from the given
	 * {@code servletRegistration}.
	 * @param servletRegistration the servlet registration
	 */
	public ServletRegistrationMappingDescription(ServletRegistration servletRegistration) {
		super(servletRegistration);
	}

	/**
	 * Returns the mappings for the registered servlet.
	 * @return the mappings
	 */
	public Collection<String> getMappings() {
		return getRegistration().getMappings();
	}

}
