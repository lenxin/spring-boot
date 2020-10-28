package org.springframework.boot.actuate.endpoint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a method on an {@link Endpoint @Endpoint} as being a delete operation.
 *


 * @since 2.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteOperation {

	/**
	 * The media type of the result of the operation.
	 * @return the media type
	 */
	String[] produces() default {};

}
