package org.springframework.boot.test.autoconfigure.web.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;
import org.springframework.util.ClassUtils;

/**
 * {@link TypeExcludeFilter} for {@link RestClientTest @RestClientTest}.
 *

 * @since 2.2.1
 */
public final class RestClientTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<RestClientTest> {

	private static final Class<?>[] NO_COMPONENTS = {};

	private static final String DATABIND_MODULE_CLASS_NAME = "com.fasterxml.jackson.databind.Module";

	private static final Set<Class<?>> DEFAULT_INCLUDES;

	static {
		Set<Class<?>> includes = new LinkedHashSet<>();
		if (ClassUtils.isPresent(DATABIND_MODULE_CLASS_NAME, RestClientTypeExcludeFilter.class.getClassLoader())) {
			try {
				includes.add(Class.forName(DATABIND_MODULE_CLASS_NAME, true,
						RestClientTypeExcludeFilter.class.getClassLoader()));
			}
			catch (ClassNotFoundException ex) {
				throw new IllegalStateException("Failed to load " + DATABIND_MODULE_CLASS_NAME, ex);
			}
			includes.add(JsonComponent.class);
		}
		DEFAULT_INCLUDES = Collections.unmodifiableSet(includes);
	}

	private final Class<?>[] components;

	RestClientTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
		this.components = getAnnotation().getValue("components", Class[].class).orElse(NO_COMPONENTS);
	}

	@Override
	protected Set<Class<?>> getDefaultIncludes() {
		return DEFAULT_INCLUDES;
	}

	@Override
	protected Set<Class<?>> getComponentIncludes() {
		return new LinkedHashSet<>(Arrays.asList(this.components));
	}

}
