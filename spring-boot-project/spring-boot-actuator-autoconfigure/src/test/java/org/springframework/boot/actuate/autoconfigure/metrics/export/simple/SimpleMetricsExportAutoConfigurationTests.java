package org.springframework.boot.actuate.autoconfigure.metrics.export.simple;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleConfig;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 *
 * Tests for {@link SimpleMetricsExportAutoConfiguration}.
 *

 */
class SimpleMetricsExportAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(SimpleMetricsExportAutoConfiguration.class));

	@Test
	void autoConfiguresConfigAndMeterRegistry() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class).run((context) -> assertThat(context)
				.hasSingleBean(SimpleMeterRegistry.class).hasSingleBean(Clock.class).hasSingleBean(SimpleConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithDefaultsEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.defaults.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(SimpleMeterRegistry.class)
						.doesNotHaveBean(SimpleConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithSpecificEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.simple.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(SimpleMeterRegistry.class)
						.doesNotHaveBean(SimpleConfig.class));
	}

	@Test
	void allowsConfigToBeCustomized() {
		this.contextRunner.withUserConfiguration(CustomConfigConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(SimpleConfig.class).hasBean("customConfig"));
	}

	@Test
	void backsOffEntirelyWithCustomMeterRegistry() {
		this.contextRunner.withUserConfiguration(CustomRegistryConfiguration.class).run((context) -> assertThat(context)
				.hasSingleBean(MeterRegistry.class).hasBean("customRegistry").doesNotHaveBean(SimpleConfig.class));
	}

	@Configuration(proxyBeanMethods = false)
	static class BaseConfiguration {

		@Bean
		Clock clock() {
			return Clock.SYSTEM;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseConfiguration.class)
	static class CustomConfigConfiguration {

		@Bean
		SimpleConfig customConfig() {
			return (key) -> null;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseConfiguration.class)
	static class CustomRegistryConfiguration {

		@Bean
		MeterRegistry customRegistry() {
			return mock(MeterRegistry.class);
		}

	}

}
