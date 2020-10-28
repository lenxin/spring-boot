package org.springframework.boot.test.autoconfigure.web.reactive;

import org.springframework.boot.test.context.ReactiveWebMergedContextConfiguration;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.TestContextBootstrapper;

/**
 * {@link TestContextBootstrapper} for {@link WebFluxTest @WebFluxTest} support.
 *


 */
class WebFluxTestContextBootstrapper extends SpringBootTestContextBootstrapper {

	@Override
	protected MergedContextConfiguration processMergedContextConfiguration(MergedContextConfiguration mergedConfig) {
		return new ReactiveWebMergedContextConfiguration(super.processMergedContextConfiguration(mergedConfig));
	}

	@Override
	protected String[] getProperties(Class<?> testClass) {
		return MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS).get(WebFluxTest.class)
				.getValue("properties", String[].class).orElse(null);
	}

}
