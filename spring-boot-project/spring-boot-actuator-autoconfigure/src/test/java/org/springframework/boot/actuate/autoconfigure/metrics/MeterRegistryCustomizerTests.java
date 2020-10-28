package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.atlas.AtlasMeterRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.atlas.AtlasMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.test.MetricsRun;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for applying {@link MeterRegistryCustomizer} beans.
 *


 */
class MeterRegistryCustomizerTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.with(MetricsRun.limitedTo(AtlasMetricsExportAutoConfiguration.class,
					PrometheusMetricsExportAutoConfiguration.class))
			.withConfiguration(AutoConfigurations.of(JvmMetricsAutoConfiguration.class));

	@Test
	void commonTagsAreAppliedToAutoConfiguredBinders() {
		this.contextRunner.withUserConfiguration(MeterRegistryCustomizerConfiguration.class).run((context) -> {
			MeterRegistry registry = context.getBean(MeterRegistry.class);
			registry.get("jvm.memory.used").tags("region", "us-east-1").gauge();
		});
	}

	@Test
	void commonTagsAreAppliedBeforeRegistryIsInjectableElsewhere() {
		this.contextRunner.withUserConfiguration(MeterRegistryCustomizerConfiguration.class).run((context) -> {
			MeterRegistry registry = context.getBean(MeterRegistry.class);
			registry.get("my.thing").tags("region", "us-east-1").gauge();
		});
	}

	@Test
	void customizersCanBeAppliedToSpecificRegistryTypes() {
		this.contextRunner.withUserConfiguration(MeterRegistryCustomizerConfiguration.class).run((context) -> {
			MeterRegistry prometheus = context.getBean(PrometheusMeterRegistry.class);
			prometheus.get("jvm.memory.used").tags("job", "myjob").gauge();
			MeterRegistry atlas = context.getBean(AtlasMeterRegistry.class);
			assertThat(atlas.find("jvm.memory.used").tags("job", "myjob").gauge()).isNull();
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class MeterRegistryCustomizerConfiguration {

		@Bean
		MeterRegistryCustomizer<MeterRegistry> commonTags() {
			return (registry) -> registry.config().commonTags("region", "us-east-1");
		}

		@Bean
		MeterRegistryCustomizer<PrometheusMeterRegistry> prometheusOnlyCommonTags() {
			return (registry) -> registry.config().commonTags("job", "myjob");
		}

		@Bean
		MyThing myThing(MeterRegistry registry) {
			registry.gauge("my.thing", 0);
			return new MyThing();
		}

		class MyThing {

		}

	}

}
