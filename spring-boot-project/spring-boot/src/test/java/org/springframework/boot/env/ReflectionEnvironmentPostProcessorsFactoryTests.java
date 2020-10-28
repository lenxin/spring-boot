package org.springframework.boot.env;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Test;

import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ReflectionEnvironmentPostProcessorsFactory}.
 *

 */
class ReflectionEnvironmentPostProcessorsFactoryTests {

	private final DeferredLogFactory logFactory = Supplier::get;

	private final DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();

	@Test
	void createWithClassesCreatesFactory() {
		ReflectionEnvironmentPostProcessorsFactory factory = new ReflectionEnvironmentPostProcessorsFactory(
				TestEnvironmentPostProcessor.class);
		assertThatFactory(factory).createsSinglePostProcessor(TestEnvironmentPostProcessor.class);
	}

	@Test
	void createWithClassNamesArrayCreatesFactory() {
		ReflectionEnvironmentPostProcessorsFactory factory = new ReflectionEnvironmentPostProcessorsFactory(
				TestEnvironmentPostProcessor.class.getName());
		assertThatFactory(factory).createsSinglePostProcessor(TestEnvironmentPostProcessor.class);
	}

	@Test
	void createWithClassNamesListCreatesFactory() {
		ReflectionEnvironmentPostProcessorsFactory factory = new ReflectionEnvironmentPostProcessorsFactory(
				Arrays.asList(TestEnvironmentPostProcessor.class.getName()));
		assertThatFactory(factory).createsSinglePostProcessor(TestEnvironmentPostProcessor.class);
	}

	@Test
	void getEnvironmentPostProcessorsWhenHasDefaultConstructorCreatesPostProcessors() {
		ReflectionEnvironmentPostProcessorsFactory factory = new ReflectionEnvironmentPostProcessorsFactory(
				TestEnvironmentPostProcessor.class.getName());
		assertThatFactory(factory).createsSinglePostProcessor(TestEnvironmentPostProcessor.class);
	}

	@Test
	void getEnvironmentPostProcessorsWhenHasLogFactoryConstructorCreatesPostProcessors() {
		ReflectionEnvironmentPostProcessorsFactory factory = new ReflectionEnvironmentPostProcessorsFactory(
				TestLogFactoryEnvironmentPostProcessor.class.getName());
		assertThatFactory(factory).createsSinglePostProcessor(TestLogFactoryEnvironmentPostProcessor.class);
	}

	@Test
	void getEnvironmentPostProcessorsWhenHasLogConstructorCreatesPostProcessors() {
		ReflectionEnvironmentPostProcessorsFactory factory = new ReflectionEnvironmentPostProcessorsFactory(
				TestLogEnvironmentPostProcessor.class.getName());
		assertThatFactory(factory).createsSinglePostProcessor(TestLogEnvironmentPostProcessor.class);
	}

	@Test
	void getEnvironmentPostProcessorsWhenHasBootstrapRegistryConstructorCreatesPostProcessors() {
		ReflectionEnvironmentPostProcessorsFactory factory = new ReflectionEnvironmentPostProcessorsFactory(
				TestBootstrapRegistryEnvironmentPostProcessor.class.getName());
		assertThatFactory(factory).createsSinglePostProcessor(TestBootstrapRegistryEnvironmentPostProcessor.class);
	}

	@Test
	void getEnvironmentPostProcessorsWhenHasNoSuitableConstructorThrowsException() {
		ReflectionEnvironmentPostProcessorsFactory factory = new ReflectionEnvironmentPostProcessorsFactory(
				BadEnvironmentPostProcessor.class.getName());
		assertThatIllegalArgumentException()
				.isThrownBy(() -> factory.getEnvironmentPostProcessors(this.logFactory, this.bootstrapContext))
				.withMessageContaining("Unable to instantiate");
	}

	private EnvironmentPostProcessorsFactoryAssert assertThatFactory(EnvironmentPostProcessorsFactory factory) {
		return new EnvironmentPostProcessorsFactoryAssert(factory);
	}

	class EnvironmentPostProcessorsFactoryAssert {

		private EnvironmentPostProcessorsFactory factory;

		EnvironmentPostProcessorsFactoryAssert(EnvironmentPostProcessorsFactory factory) {
			this.factory = factory;
		}

		void createsSinglePostProcessor(Class<?> expectedType) {
			List<EnvironmentPostProcessor> processors = this.factory.getEnvironmentPostProcessors(
					ReflectionEnvironmentPostProcessorsFactoryTests.this.logFactory,
					ReflectionEnvironmentPostProcessorsFactoryTests.this.bootstrapContext);
			assertThat(processors).hasSize(1);
			assertThat(processors.get(0)).isInstanceOf(expectedType);
		}

	}

	static class TestEnvironmentPostProcessor implements EnvironmentPostProcessor {

		@Override
		public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		}

	}

	static class TestLogFactoryEnvironmentPostProcessor implements EnvironmentPostProcessor {

		TestLogFactoryEnvironmentPostProcessor(DeferredLogFactory logFactory) {
			assertThat(logFactory).isNotNull();
		}

		@Override
		public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		}

	}

	static class TestLogEnvironmentPostProcessor implements EnvironmentPostProcessor {

		TestLogEnvironmentPostProcessor(Log log) {
			assertThat(log).isNotNull();
		}

		@Override
		public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		}

	}

	static class TestBootstrapRegistryEnvironmentPostProcessor implements EnvironmentPostProcessor {

		TestBootstrapRegistryEnvironmentPostProcessor(BootstrapRegistry bootstrapRegistry) {
			assertThat(bootstrapRegistry).isNotNull();
		}

		@Override
		public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		}

	}

	static class BadEnvironmentPostProcessor implements EnvironmentPostProcessor {

		BadEnvironmentPostProcessor(InputStream inputStream) {
		}

		@Override
		public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		}

	}

}
