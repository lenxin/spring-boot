package org.springframework.boot.test.autoconfigure.webservices.client;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.TestContextBootstrapper;

/**
 * {@link TestContextBootstrapper} for {@link WebServiceClientTest @WebServiceClientTest}
 * support.
 *

 */
class WebServiceClientTestContextBootstrapper extends SpringBootTestContextBootstrapper {

	@Override
	protected String[] getProperties(Class<?> testClass) {
		return MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS).get(WebServiceClientTest.class)
				.getValue("properties", String[].class).orElse(null);
	}

}
