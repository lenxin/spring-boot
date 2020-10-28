package org.springframework.boot.actuate.autoconfigure.metrics.export.atlas;

import com.netflix.spectator.atlas.AtlasConfig;
import io.micrometer.atlas.AtlasMeterRegistry;
import io.micrometer.core.instrument.Clock;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AtlasMetricsExportAutoConfiguration}.
 *

 */
class AtlasMetricsExportAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(AtlasMetricsExportAutoConfiguration.class));

	@Test
	void backsOffWithoutAClock() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(AtlasMeterRegistry.class));
	}

	@Test
	void autoConfiguresItsConfigAndMeterRegistry() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class).run((context) -> assertThat(context)
				.hasSingleBean(AtlasMeterRegistry.class).hasSingleBean(AtlasConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithDefaultsEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.defaults.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(AtlasMeterRegistry.class)
						.doesNotHaveBean(AtlasConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithSpecificEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.atlas.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(AtlasMeterRegistry.class)
						.doesNotHaveBean(AtlasConfig.class));
	}

	@Test
	void allowsCustomConfigToBeUsed() {
		this.contextRunner.withUserConfiguration(CustomConfigConfiguration.class).run((context) -> assertThat(context)
				.hasSingleBean(AtlasMeterRegistry.class).hasSingleBean(AtlasConfig.class).hasBean("customConfig"));
	}

	@Test
	void allowsCustomRegistryToBeUsed() {
		this.contextRunner.withUserConfiguration(CustomRegistryConfiguration.class).run((context) -> assertThat(context)
				.hasSingleBean(AtlasMeterRegistry.class).hasBean("customRegistry").hasSingleBean(AtlasConfig.class));
	}

	@Test
	void stopsMeterRegistryWhenContextIsClosed() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class).run((context) -> {
			AtlasMeterRegistry registry = context.getBean(AtlasMeterRegistry.class);
			assertThat(registry.isClosed()).isFalse();
			context.close();
			assertThat(registry.isClosed()).isTrue();
		});
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
		AtlasConfig customConfig() {
			return (key) -> null;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseConfiguration.class)
	static class CustomRegistryConfiguration {

		@Bean
		AtlasMeterRegistry customRegistry(AtlasConfig config, Clock clock) {
			return new AtlasMeterRegistry(config, clock);
		}

	}

}
