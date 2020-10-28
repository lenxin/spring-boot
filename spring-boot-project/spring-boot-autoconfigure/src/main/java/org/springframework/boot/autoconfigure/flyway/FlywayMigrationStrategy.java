package org.springframework.boot.autoconfigure.flyway;

import org.flywaydb.core.Flyway;

/**
 * Strategy used to initialize {@link Flyway} migration. Custom implementations may be
 * registered as a {@code @Bean} to override the default migration behavior.
 *


 * @since 1.3.0
 */
@FunctionalInterface
public interface FlywayMigrationStrategy {

	/**
	 * Trigger flyway migration.
	 * @param flyway the flyway instance
	 */
	void migrate(Flyway flyway);

}
