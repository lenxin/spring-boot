package org.springframework.boot.diagnostics.analyzer;

import org.junit.jupiter.api.Test;

import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalyzer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for {@link BeanNotOfRequiredTypeFailureAnalyzer}.
 *

 */
class BeanNotOfRequiredTypeFailureAnalyzerTests {

	private final FailureAnalyzer analyzer = new BeanNotOfRequiredTypeFailureAnalyzer();

	@Test
	void jdkProxyCausesInjectionFailure() {
		FailureAnalysis analysis = performAnalysis(JdkProxyConfiguration.class);
		assertThat(analysis.getDescription()).startsWith("The bean 'asyncBean'");
		assertThat(analysis.getDescription()).contains("'" + AsyncBean.class.getName() + "'");
		assertThat(analysis.getDescription()).endsWith(String.format("%s%n", SomeInterface.class.getName()));
	}

	private FailureAnalysis performAnalysis(Class<?> configuration) {
		FailureAnalysis analysis = this.analyzer.analyze(createFailure(configuration));
		assertThat(analysis).isNotNull();
		return analysis;
	}

	private Exception createFailure(Class<?> configuration) {
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(configuration)) {
			fail("Expected failure did not occur");
			return null;
		}
		catch (Exception ex) {
			return ex;
		}
	}

	@Configuration(proxyBeanMethods = false)
	@EnableAsync
	@Import(UserConfiguration.class)
	static class JdkProxyConfiguration {

		@Bean
		AsyncBean asyncBean() {
			return new AsyncBean();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class UserConfiguration {

		@Bean
		AsyncBeanUser user(AsyncBean bean) {
			return new AsyncBeanUser(bean);
		}

	}

	static class AsyncBean implements SomeInterface {

		@Async
		void foo() {

		}

		@Override
		public void bar() {

		}

	}

	interface SomeInterface {

		void bar();

	}

	static class AsyncBeanUser {

		AsyncBeanUser(AsyncBean asyncBean) {
		}

	}

}
