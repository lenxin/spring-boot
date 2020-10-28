package org.springframework.boot.test.mock.mockito;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container annotation that aggregates several {@link SpyBean @SpyBean} annotations.
 * <p>
 * Can be used natively, declaring several nested {@link SpyBean @SpyBean} annotations.
 * Can also be used in conjunction with Java 8's support for <em>repeatable
 * annotations</em>, where {@link SpyBean @SpyBean} can simply be declared several times
 * on the same {@linkplain ElementType#TYPE type}, implicitly generating this container
 * annotation.
 *

 * @since 1.4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface SpyBeans {

	/**
	 * Return the contained {@link SpyBean @SpyBean} annotations.
	 * @return the spy beans
	 */
	SpyBean[] value();

}
