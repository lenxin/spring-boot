package org.springframework.boot.test.autoconfigure.properties;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Example {@link PropertyMapping @PropertyMapping} annotation for use with
 * {@link PropertyMappingTests}.
 *

 */
@Retention(RetentionPolicy.RUNTIME)
@PropertyMapping
@interface ExampleMapping {

	String exampleProperty();

}
