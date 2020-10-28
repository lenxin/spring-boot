package org.springframework.boot.docs.context.properties.bind.constructor;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.ContextConsumer;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AppSystemProperties}.
 *

 */
class AppSystemPropertiesTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(Config.class);

	@Test
	void bindWithDefaultUnit() {
		this.contextRunner.withPropertyValues("app.system.session-timeout=40", "app.system.read-timeout=5000")
				.run(assertBinding((properties) -> {
					assertThat(properties.getSessionTimeout()).hasSeconds(40);
					assertThat(properties.getReadTimeout()).hasMillis(5000);
				}));
	}

	@Test
	void bindWithExplicitUnit() {
		this.contextRunner.withPropertyValues("app.system.session-timeout=1h", "app.system.read-timeout=5s")
				.run(assertBinding((properties) -> {
					assertThat(properties.getSessionTimeout()).hasMinutes(60);
					assertThat(properties.getReadTimeout()).hasMillis(5000);
				}));
	}

	@Test
	void bindWithIso8601Format() {
		this.contextRunner.withPropertyValues("app.system.session-timeout=PT15S", "app.system.read-timeout=PT0.5S")
				.run(assertBinding((properties) -> {
					assertThat(properties.getSessionTimeout()).hasSeconds(15);
					assertThat(properties.getReadTimeout()).hasMillis(500);
				}));
	}

	private ContextConsumer<AssertableApplicationContext> assertBinding(Consumer<AppSystemProperties> properties) {
		return (context) -> {
			assertThat(context).hasSingleBean(AppSystemProperties.class);
			properties.accept(context.getBean(AppSystemProperties.class));
		};
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(AppSystemProperties.class)
	static class Config {

	}

}
