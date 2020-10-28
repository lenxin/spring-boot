package org.springframework.boot.autoconfigure.flyway;

import org.flywaydb.core.Flyway;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

/**
 * {@link InitializingBean} used to trigger {@link Flyway} migration via the
 * {@link FlywayMigrationStrategy}.
 *

 * @since 1.3.0
 */
public class FlywayMigrationInitializer implements InitializingBean, Ordered {

	private final Flyway flyway;

	private final FlywayMigrationStrategy migrationStrategy;

	private int order = 0;

	/**
	 * Create a new {@link FlywayMigrationInitializer} instance.
	 * @param flyway the flyway instance
	 */
	public FlywayMigrationInitializer(Flyway flyway) {
		this(flyway, null);
	}

	/**
	 * Create a new {@link FlywayMigrationInitializer} instance.
	 * @param flyway the flyway instance
	 * @param migrationStrategy the migration strategy or {@code null}
	 */
	public FlywayMigrationInitializer(Flyway flyway, FlywayMigrationStrategy migrationStrategy) {
		Assert.notNull(flyway, "Flyway must not be null");
		this.flyway = flyway;
		this.migrationStrategy = migrationStrategy;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.migrationStrategy != null) {
			this.migrationStrategy.migrate(this.flyway);
		}
		else {
			try {
				this.flyway.migrate();
			}
			catch (NoSuchMethodError ex) {
				// Flyway < 7.0
				this.flyway.getClass().getMethod("migrate").invoke(this.flyway);
			}
		}
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
