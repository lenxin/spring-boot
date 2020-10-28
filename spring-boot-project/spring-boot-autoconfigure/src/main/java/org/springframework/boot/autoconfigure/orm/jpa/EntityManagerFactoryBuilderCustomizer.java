package org.springframework.boot.autoconfigure.orm.jpa;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

/**
 * Callback interface that can be used to customize the auto-configured
 * {@link EntityManagerFactoryBuilder}.
 *

 * @since 2.1.0
 */
@FunctionalInterface
public interface EntityManagerFactoryBuilderCustomizer {

	/**
	 * Customize the given {@code builder}.
	 * @param builder the builder to customize
	 */
	void customize(EntityManagerFactoryBuilder builder);

}
