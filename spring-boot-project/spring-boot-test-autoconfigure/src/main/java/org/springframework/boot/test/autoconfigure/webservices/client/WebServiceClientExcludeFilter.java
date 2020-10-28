package org.springframework.boot.test.autoconfigure.webservices.client;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link WebServiceClientTest @WebServiceClientTest}.
 *

 * @since 2.3.0
 */
public final class WebServiceClientExcludeFilter
		extends StandardAnnotationCustomizableTypeExcludeFilter<WebServiceClientTest> {

	private final Class<?>[] components;

	protected WebServiceClientExcludeFilter(Class<?> testClass) {
		super(testClass);
		this.components = getAnnotation().getValue("components", Class[].class).orElseGet(() -> new Class<?>[0]);
	}

	@Override
	protected Set<Class<?>> getComponentIncludes() {
		return new LinkedHashSet<>(Arrays.asList(this.components));
	}

}
