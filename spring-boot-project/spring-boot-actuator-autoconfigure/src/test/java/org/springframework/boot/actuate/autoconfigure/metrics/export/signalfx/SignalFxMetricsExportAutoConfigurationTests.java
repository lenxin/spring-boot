package org.springframework.boot.actuate.autoconfigure.metrics.export.signalfx;

import io.micrometer.core.instrument.Clock;
import io.micrometer.signalfx.SignalFxConfig;
import io.micrometer.signalfx.SignalFxMeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SignalFxMetricsExportAutoConfiguration}.
 *

 */
class SignalFxMetricsExportAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(SignalFxMetricsExportAutoConfiguration.class));

	@Test
	void backsOffWithoutAClock() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(SignalFxMeterRegistry.class));
	}

	@Test
	void failsWithoutAnAccessToken() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.run((context) -> assertThat(context).hasFailed());
	}

	@Test
	void autoConfiguresWithAnAccessToken() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.signalfx.access-token=abcde")
				.run((context) -> assertThat(context).hasSingleBean(SignalFxMeterRegistry.class)
						.hasSingleBean(Clock.class).hasSingleBean(SignalFxConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithDefaultsEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.defaults.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(SignalFxMeterRegistry.class)
						.doesNotHaveBean(SignalFxConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithSpecificEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.signalfx.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(SignalFxMeterRegistry.class)
						.doesNotHaveBean(SignalFxConfig.class));
	}

	@Test
	void allowsConfigToBeCustomized() {
		this.contextRunner.withPropertyValues("management.metrics.export.signalfx.access-token=abcde")
				.withUserConfiguration(CustomConfigConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(Clock.class)
						.hasSingleBean(SignalFxMeterRegistry.class).hasSingleBean(SignalFxConfig.class)
						.hasBean("customConfig"));
	}

	@Test
	void allowsRegistryToBeCustomized() {
		this.contextRunner.withPropertyValues("management.metrics.export.signalfx.access-token=abcde")
				.withUserConfiguration(CustomRegistryConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(Clock.class).hasSingleBean(SignalFxConfig.class)
						.hasSingleBean(SignalFxMeterRegistry.class).hasBean("customRegistry"));
	}

	@Test
	void stopsMeterRegistryWhenContextIsClosed() {
		this.contextRunner.withPropertyValues("management.metrics.export.signalfx.access-token=abcde")
				.withUserConfiguration(BaseConfiguration.class).run((context) -> {
					SignalFxMeterRegistry registry = context.getBean(SignalFxMeterRegistry.class);
					assertThat(registry.isClosed()).isFalse();
					context.close();
					assertThat(registry.isClosed()).isTrue();
				});
	}

	@Configuration(proxyBeanMethods = false)
	static class BaseConfiguration {

		@Bean
		Clock customClock() {
			return Clock.SYSTEM;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseConfiguration.class)
	static class CustomConfigConfiguration {

		@Bean
		SignalFxConfig customConfig() {
			return (key) -> {
				if ("signalfx.accessToken".equals(key)) {
					return "abcde";
				}
				return null;
			};
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseConfiguration.class)
	static class CustomRegistryConfiguration {

		@Bean
		SignalFxMeterRegistry customRegistry(SignalFxConfig config, Clock clock) {
			return new SignalFxMeterRegistry(config, clock);
		}

	}

}
