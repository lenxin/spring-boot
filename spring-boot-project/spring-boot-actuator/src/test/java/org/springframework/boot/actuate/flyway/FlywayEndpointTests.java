package org.springframework.boot.actuate.flyway;

import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.flyway.FlywayEndpoint.FlywayDescriptor;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FlywayEndpoint}.
 *



 */
class FlywayEndpointTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(FlywayAutoConfiguration.class))
			.withUserConfiguration(EmbeddedDataSourceConfiguration.class).withBean("endpoint", FlywayEndpoint.class);

	@Test
	void flywayReportIsProduced() {
		this.contextRunner.run((context) -> {
			Map<String, FlywayDescriptor> flywayBeans = context.getBean(FlywayEndpoint.class).flywayBeans()
					.getContexts().get(context.getId()).getFlywayBeans();
			assertThat(flywayBeans).hasSize(1);
			assertThat(flywayBeans.values().iterator().next().getMigrations()).hasSize(3);
		});
	}

	@Test
	void whenFlywayHasBeenBaselinedFlywayReportIsProduced() {
		this.contextRunner.withPropertyValues("spring.flyway.baseline-version=2")
				.withBean(FlywayMigrationStrategy.class, () -> (flyway) -> {
					flyway.baseline();
					flyway.migrate();
				}).run((context) -> {
					Map<String, FlywayDescriptor> flywayBeans = context.getBean(FlywayEndpoint.class).flywayBeans()
							.getContexts().get(context.getId()).getFlywayBeans();
					assertThat(flywayBeans).hasSize(1);
					assertThat(flywayBeans.values().iterator().next().getMigrations()).hasSize(3);
				});
	}

}
