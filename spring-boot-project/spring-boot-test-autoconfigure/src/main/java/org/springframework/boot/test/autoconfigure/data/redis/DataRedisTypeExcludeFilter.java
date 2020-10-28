package org.springframework.boot.test.autoconfigure.data.redis;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link DataRedisTest @DataRedisTest}.
 *

 * @since 2.2.1
 */
public final class DataRedisTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<DataRedisTest> {

	DataRedisTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
