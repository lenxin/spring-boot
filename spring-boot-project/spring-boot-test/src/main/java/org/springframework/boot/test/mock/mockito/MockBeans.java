package org.springframework.boot.test.mock.mockito;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container annotation that aggregates several {@link MockBean @MockBean} annotations.
 * <p>
 * Can be used natively, declaring several nested {@link MockBean @MockBean} annotations.
 * Can also be used in conjunction with Java 8's support for <em>repeatable
 * annotations</em>, where {@link MockBean @MockBean} can simply be declared several times
 * on the same {@linkplain ElementType#TYPE type}, implicitly generating this container
 * annotation.
 *

 * @since 1.4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface MockBeans {

	/**
	 * Return the contained {@link MockBean @MockBean} annotations.
	 * @return the mock beans
	 */
	MockBean[] value();

}
