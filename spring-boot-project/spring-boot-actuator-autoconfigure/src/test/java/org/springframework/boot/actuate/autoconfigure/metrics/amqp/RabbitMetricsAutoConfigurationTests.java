package org.springframework.boot.actuate.autoconfigure.metrics.amqp;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.test.MetricsRun;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RabbitMetricsAutoConfiguration}.
 *

 */
class RabbitMetricsAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().with(MetricsRun.simple())
			.withConfiguration(
					AutoConfigurations.of(RabbitAutoConfiguration.class, RabbitMetricsAutoConfiguration.class));

	@Test
	void autoConfiguredConnectionFactoryIsInstrumented() {
		this.contextRunner.run((context) -> {
			MeterRegistry registry = context.getBean(MeterRegistry.class);
			registry.get("rabbitmq.connections").meter();
		});
	}

	@Test
	void rabbitmqNativeConnectionFactoryInstrumentationCanBeDisabled() {
		this.contextRunner.withPropertyValues("management.metrics.enable.rabbitmq=false").run((context) -> {
			MeterRegistry registry = context.getBean(MeterRegistry.class);
			assertThat(registry.find("rabbitmq.connections").meter()).isNull();
		});
	}

}
