package org.springframework.boot.actuate.autoconfigure.metrics.r2dbc;

import java.util.Collections;
import java.util.UUID;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.r2dbc.h2.CloseableConnectionFactory;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.test.MetricsRun;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ConnectionPoolMetricsAutoConfiguration}.
 *


 */
class ConnectionPoolMetricsAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withPropertyValues("spring.r2dbc.generate-unique-name=true").with(MetricsRun.simple())
			.withConfiguration(AutoConfigurations.of(ConnectionPoolMetricsAutoConfiguration.class))
			.withUserConfiguration(BaseConfiguration.class);

	@Test
	void autoConfiguredDataSourceIsInstrumented() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(R2dbcAutoConfiguration.class)).run((context) -> {
			MeterRegistry registry = context.getBean(MeterRegistry.class);
			assertThat(registry.find("r2dbc.pool.acquired").gauges()).hasSize(1);
		});
	}

	@Test
	void autoConfiguredDataSourceExposedAsConnectionFactoryTypeIsInstrumented() {
		this.contextRunner
				.withPropertyValues(
						"spring.r2dbc.url:r2dbc:pool:h2:mem:///name?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
				.withConfiguration(AutoConfigurations.of(R2dbcAutoConfiguration.class)).run((context) -> {
					MeterRegistry registry = context.getBean(MeterRegistry.class);
					assertThat(registry.find("r2dbc.pool.acquired").gauges()).hasSize(1);
				});
	}

	@Test
	void connectionPoolInstrumentationCanBeDisabled() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(R2dbcAutoConfiguration.class))
				.withPropertyValues("management.metrics.enable.r2dbc=false").run((context) -> {
					MeterRegistry registry = context.getBean(MeterRegistry.class);
					assertThat(registry.find("r2dbc.pool.acquired").gauge()).isNull();
				});
	}

	@Test
	void connectionPoolExposedAsConnectionFactoryTypeIsInstrumented() {
		this.contextRunner.withUserConfiguration(ConnectionFactoryConfiguration.class).run((context) -> {
			MeterRegistry registry = context.getBean(MeterRegistry.class);
			assertThat(registry.find("r2dbc.pool.acquired").gauges()).extracting(Meter::getId)
					.extracting((id) -> id.getTag("name")).containsExactly("testConnectionPool");
		});
	}

	@Test
	void allConnectionPoolsCanBeInstrumented() {
		this.contextRunner.withUserConfiguration(TwoConnectionPoolsConfiguration.class).run((context) -> {
			MeterRegistry registry = context.getBean(MeterRegistry.class);
			assertThat(registry.find("r2dbc.pool.acquired").gauges()).extracting(Meter::getId)
					.extracting((id) -> id.getTag("name")).containsExactlyInAnyOrder("firstPool", "secondPool");
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class BaseConfiguration {

		@Bean
		SimpleMeterRegistry registry() {
			return new SimpleMeterRegistry();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class ConnectionFactoryConfiguration {

		@Bean
		ConnectionFactory testConnectionPool() {
			return new ConnectionPool(
					ConnectionPoolConfiguration.builder(H2ConnectionFactory.inMemory("db-" + UUID.randomUUID(), "sa",
							"", Collections.singletonMap(H2ConnectionOption.DB_CLOSE_DELAY, "-1"))).build());
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class TwoConnectionPoolsConfiguration {

		@Bean
		CloseableConnectionFactory connectionFactory() {
			return H2ConnectionFactory.inMemory("db-" + UUID.randomUUID(), "sa", "",
					Collections.singletonMap(H2ConnectionOption.DB_CLOSE_DELAY, "-1"));
		}

		@Bean
		ConnectionPool firstPool(ConnectionFactory connectionFactory) {
			return new ConnectionPool(ConnectionPoolConfiguration.builder(connectionFactory).build());
		}

		@Bean
		ConnectionPool secondPool(ConnectionFactory connectionFactory) {
			return new ConnectionPool(ConnectionPoolConfiguration.builder(connectionFactory).build());
		}

	}

}
