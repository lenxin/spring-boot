package org.springframework.boot.test.autoconfigure.filter;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.util.ObjectUtils;

/**
 * {@link ContextCustomizerFactory} to support
 * {@link TypeExcludeFilters @TypeExcludeFilters}.
 *

 * @see TypeExcludeFiltersContextCustomizer
 */
class TypeExcludeFiltersContextCustomizerFactory implements ContextCustomizerFactory {

	private static final Class<?>[] NO_FILTERS = {};

	@Override
	public ContextCustomizer createContextCustomizer(Class<?> testClass,
			List<ContextConfigurationAttributes> configurationAttributes) {
		Class<?>[] filterClasses = MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS)
				.get(TypeExcludeFilters.class).getValue(MergedAnnotation.VALUE, Class[].class).orElse(NO_FILTERS);
		if (ObjectUtils.isEmpty(filterClasses)) {
			return null;
		}
		return createContextCustomizer(testClass, filterClasses);
	}

	@SuppressWarnings("unchecked")
	private ContextCustomizer createContextCustomizer(Class<?> testClass, Class<?>[] filterClasses) {
		return new TypeExcludeFiltersContextCustomizer(testClass,
				new LinkedHashSet<>(Arrays.asList((Class<? extends TypeExcludeFilter>[]) filterClasses)));
	}

}
