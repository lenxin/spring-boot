package org.springframework.boot.test.autoconfigure.data.mongo;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link DataMongoTest @DataMongoTest}.
 *

 * @since 2.2.1
 */
public final class DataMongoTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<DataMongoTest> {

	DataMongoTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
