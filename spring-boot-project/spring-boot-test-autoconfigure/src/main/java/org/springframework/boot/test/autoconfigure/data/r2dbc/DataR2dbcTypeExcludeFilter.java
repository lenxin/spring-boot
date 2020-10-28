package org.springframework.boot.test.autoconfigure.data.r2dbc;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link DataR2dbcTest @DataR2dbcTest}.
 *

 */
class DataR2dbcTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<DataR2dbcTest> {

	DataR2dbcTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
