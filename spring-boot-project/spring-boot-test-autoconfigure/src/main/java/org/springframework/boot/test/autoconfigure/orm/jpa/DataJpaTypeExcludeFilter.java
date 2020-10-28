package org.springframework.boot.test.autoconfigure.orm.jpa;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link DataJpaTest @DataJpaTest}.
 *

 * @since 2.2.1
 */
public final class DataJpaTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<DataJpaTest> {

	DataJpaTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
