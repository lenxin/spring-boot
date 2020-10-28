package org.springframework.boot.autoconfigure.context;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.DefaultLifecycleProcessor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LifecycleAutoConfiguration}.
 *

 */
public class LifecycleAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(LifecycleAutoConfiguration.class));

	@Test
	void lifecycleProcessorIsConfiguredWithDefaultTimeout() {
		this.contextRunner.run((context) -> {
			assertThat(context).hasBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME);
			Object processor = context.getBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME);
			assertThat(processor).extracting("timeoutPerShutdownPhase").isEqualTo(30000L);
		});
	}

	@Test
	void lifecycleProcessorIsConfiguredWithCustomDefaultTimeout() {
		this.contextRunner.withPropertyValues("spring.lifecycle.timeout-per-shutdown-phase=15s").run((context) -> {
			assertThat(context).hasBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME);
			Object processor = context.getBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME);
			assertThat(processor).extracting("timeoutPerShutdownPhase").isEqualTo(15000L);
		});
	}

	@Test
	void lifecycleProcessorIsConfiguredWithCustomDefaultTimeoutInAChildContext() {
		new ApplicationContextRunner().run((parent) -> {
			this.contextRunner.withParent(parent).withPropertyValues("spring.lifecycle.timeout-per-shutdown-phase=15s")
					.run((child) -> {
						assertThat(child).hasBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME);
						Object processor = child.getBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME);
						assertThat(processor).extracting("timeoutPerShutdownPhase").isEqualTo(15000L);
					});
		});
	}

	@Test
	void whenUserDefinesALifecycleProcessorBeanThenTheAutoConfigurationBacksOff() {
		this.contextRunner.withUserConfiguration(LifecycleProcessorConfiguration.class).run((context) -> {
			assertThat(context).hasBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME);
			Object processor = context.getBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME);
			assertThat(processor).extracting("timeoutPerShutdownPhase").isEqualTo(5000L);
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class LifecycleProcessorConfiguration {

		@Bean(name = AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME)
		DefaultLifecycleProcessor customLifecycleProcessor() {
			DefaultLifecycleProcessor processor = new DefaultLifecycleProcessor();
			processor.setTimeoutPerShutdownPhase(5000);
			return processor;
		}

	}

}
