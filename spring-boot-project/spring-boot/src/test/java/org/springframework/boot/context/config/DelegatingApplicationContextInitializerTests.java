package org.springframework.boot.context.config;

import org.junit.jupiter.api.Test;

import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link DelegatingApplicationContextInitializer}.
 *

 */
class DelegatingApplicationContextInitializerTests {

	private final DelegatingApplicationContextInitializer initializer = new DelegatingApplicationContextInitializer();

	@Test
	void orderedInitialize() {
		StaticApplicationContext context = new StaticApplicationContext();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context,
				"context.initializer.classes=" + MockInitB.class.getName() + "," + MockInitA.class.getName());
		this.initializer.initialize(context);
		assertThat(context.getBeanFactory().getSingleton("a")).isEqualTo("a");
		assertThat(context.getBeanFactory().getSingleton("b")).isEqualTo("b");
	}

	@Test
	void noInitializers() {
		StaticApplicationContext context = new StaticApplicationContext();
		this.initializer.initialize(context);
	}

	@Test
	void emptyInitializers() {
		StaticApplicationContext context = new StaticApplicationContext();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context, "context.initializer.classes:");
		this.initializer.initialize(context);
	}

	@Test
	void noSuchInitializerClass() {
		StaticApplicationContext context = new StaticApplicationContext();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context,
				"context.initializer.classes=missing.madeup.class");
		assertThatExceptionOfType(ApplicationContextException.class)
				.isThrownBy(() -> this.initializer.initialize(context));
	}

	@Test
	void notAnInitializerClass() {
		StaticApplicationContext context = new StaticApplicationContext();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context,
				"context.initializer.classes=" + Object.class.getName());
		assertThatIllegalArgumentException().isThrownBy(() -> this.initializer.initialize(context));
	}

	@Test
	void genericNotSuitable() {
		StaticApplicationContext context = new StaticApplicationContext();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context,
				"context.initializer.classes=" + NotSuitableInit.class.getName());
		assertThatIllegalArgumentException().isThrownBy(() -> this.initializer.initialize(context))
				.withMessageContaining("generic parameter");
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	static class MockInitA implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			applicationContext.getBeanFactory().registerSingleton("a", "a");
		}

	}

	@Order(Ordered.LOWEST_PRECEDENCE)
	static class MockInitB implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			assertThat(applicationContext.getBeanFactory().getSingleton("a")).isEqualTo("a");
			applicationContext.getBeanFactory().registerSingleton("b", "b");
		}

	}

	static class NotSuitableInit implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

		@Override
		public void initialize(ConfigurableWebApplicationContext applicationContext) {
		}

	}

}
