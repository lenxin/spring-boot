package org.springframework.boot.actuate.autoconfigure.metrics.export.graphite;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Tags;
import io.micrometer.graphite.GraphiteConfig;
import io.micrometer.graphite.GraphiteMeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link GraphiteMetricsExportAutoConfiguration}.
 *


 */
class GraphiteMetricsExportAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(GraphiteMetricsExportAutoConfiguration.class));

	@Test
	void backsOffWithoutAClock() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(GraphiteMeterRegistry.class));
	}

	@Test
	void autoConfiguresUseTagsAsPrefix() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.graphite.tags-as-prefix=app").run((context) -> {
					assertThat(context).hasSingleBean(GraphiteMeterRegistry.class);
					GraphiteMeterRegistry registry = context.getBean(GraphiteMeterRegistry.class);
					registry.counter("test.count", Tags.of("app", "myapp"));
					assertThat(registry.getDropwizardRegistry().getMeters()).containsOnlyKeys("myapp.testCount");
				});
	}

	@Test
	void autoConfiguresWithTagsAsPrefixCanBeDisabled() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.graphite.tags-as-prefix=app",
						"management.metrics.export.graphite.graphite-tags-enabled=true")
				.run((context) -> {
					assertThat(context).hasSingleBean(GraphiteMeterRegistry.class);
					GraphiteMeterRegistry registry = context.getBean(GraphiteMeterRegistry.class);
					registry.counter("test.count", Tags.of("app", "myapp"));
					assertThat(registry.getDropwizardRegistry().getMeters()).containsOnlyKeys("test.count;app=myapp");
				});
	}

	@Test
	void autoConfiguresItsConfigAndMeterRegistry() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class).run((context) -> assertThat(context)
				.hasSingleBean(GraphiteMeterRegistry.class).hasSingleBean(GraphiteConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithDefaultsEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.defaults.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(GraphiteMeterRegistry.class)
						.doesNotHaveBean(GraphiteConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithSpecificEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.graphite.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(GraphiteMeterRegistry.class)
						.doesNotHaveBean(GraphiteConfig.class));
	}

	@Test
	void allowsCustomConfigToBeUsed() {
		this.contextRunner.withUserConfiguration(CustomConfigConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(GraphiteMeterRegistry.class)
						.hasSingleBean(GraphiteConfig.class).hasBean("customConfig"));
	}

	@Test
	void allowsCustomRegistryToBeUsed() {
		this.contextRunner.withUserConfiguration(CustomRegistryConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(GraphiteMeterRegistry.class)
						.hasBean("customRegistry").hasSingleBean(GraphiteConfig.class));
	}

	@Test
	void stopsMeterRegistryWhenContextIsClosed() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class).run((context) -> {
			GraphiteMeterRegistry registry = context.getBean(GraphiteMeterRegistry.class);
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
		GraphiteConfig customConfig() {
			return (key) -> {
				if ("Graphite.apiKey".equals(key)) {
					return "12345";
				}
				return null;
			};
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseConfiguration.class)
	static class CustomRegistryConfiguration {

		@Bean
		GraphiteMeterRegistry customRegistry(GraphiteConfig config, Clock clock) {
			return new GraphiteMeterRegistry(config, clock);
		}

	}

}
