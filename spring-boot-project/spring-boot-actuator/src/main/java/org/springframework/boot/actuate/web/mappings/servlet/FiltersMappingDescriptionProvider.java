package org.springframework.boot.actuate.web.mappings.servlet;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.springframework.boot.actuate.web.mappings.MappingDescriptionProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * A {@link MappingDescriptionProvider} that describes that mappings of any {@link Filter
 * Filters} registered with a {@link ServletContext}.
 *

 * @since 2.0.0
 */
public class FiltersMappingDescriptionProvider implements MappingDescriptionProvider {

	@Override
	public List<FilterRegistrationMappingDescription> describeMappings(ApplicationContext context) {
		if (!(context instanceof WebApplicationContext)) {
			return Collections.emptyList();
		}
		return ((WebApplicationContext) context).getServletContext().getFilterRegistrations().values().stream()
				.map(FilterRegistrationMappingDescription::new).collect(Collectors.toList());
	}

	@Override
	public String getMappingName() {
		return "servletFilters";
	}

}
