package org.springframework.boot.context.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * Enable support for {@link ConfigurationProperties @ConfigurationProperties} annotated
 * beans. {@code @ConfigurationProperties} beans can be registered in the standard way
 * (for example using {@link Bean @Bean} methods) or, for convenience, can be specified
 * directly on this annotation.
 *

 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnableConfigurationPropertiesRegistrar.class)
public @interface EnableConfigurationProperties {

	/**
	 * The bean name of the configuration properties validator.
	 * @since 2.2.0
	 */
	String VALIDATOR_BEAN_NAME = "configurationPropertiesValidator";

	/**
	 * Convenient way to quickly register
	 * {@link ConfigurationProperties @ConfigurationProperties} annotated beans with
	 * Spring. Standard Spring Beans will also be scanned regardless of this value.
	 * @return {@code @ConfigurationProperties} annotated beans to register
	 */
	Class<?>[] value() default {};

}
