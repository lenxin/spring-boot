package org.springframework.boot.test.autoconfigure.web.servlet;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.TestContextBootstrapper;
import org.springframework.test.context.web.WebMergedContextConfiguration;

/**
 * {@link TestContextBootstrapper} for {@link WebMvcTest @WebMvcTest} support.
 *



 */
class WebMvcTestContextBootstrapper extends SpringBootTestContextBootstrapper {

	@Override
	protected MergedContextConfiguration processMergedContextConfiguration(MergedContextConfiguration mergedConfig) {
		MergedContextConfiguration processedMergedConfiguration = super.processMergedContextConfiguration(mergedConfig);
		return new WebMergedContextConfiguration(processedMergedConfiguration, determineResourceBasePath(mergedConfig));
	}

	@Override
	protected String[] getProperties(Class<?> testClass) {
		return MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS).get(WebMvcTest.class)
				.getValue("properties", String[].class).orElse(null);
	}

}
