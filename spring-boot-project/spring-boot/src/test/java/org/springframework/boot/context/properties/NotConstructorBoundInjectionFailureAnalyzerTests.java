package org.springframework.boot.context.properties;

import org.junit.jupiter.api.Test;

import org.springframework.beans.FatalBeanException;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NotConstructorBoundInjectionFailureAnalyzer}.
 *

 */
class NotConstructorBoundInjectionFailureAnalyzerTests {

	private final NotConstructorBoundInjectionFailureAnalyzer analyzer = new NotConstructorBoundInjectionFailureAnalyzer();

	@Test
	void failureAnalysisForConfigurationPropertiesThatShouldHaveBeenConstructorBound() {
		FailureAnalysis analysis = analyzeFailure(
				createFailure(ShouldHaveUsedConstructorBindingPropertiesConfiguration.class));
		assertThat(analysis.getDescription()).isEqualTo(ConstructorBoundProperties.class.getSimpleName()
				+ " is annotated with @" + ConstructorBinding.class.getSimpleName()
				+ " but it is defined as a regular bean which caused dependency injection to fail.");
		assertThat(analysis.getAction())
				.isEqualTo("Update your configuration so that " + ConstructorBoundProperties.class.getSimpleName()
						+ " is defined via @" + ConfigurationPropertiesScan.class.getSimpleName() + " or @"
						+ EnableConfigurationProperties.class.getSimpleName() + ".");
	}

	@Test
	void failureAnalysisForNonConstructorBoundProperties() {
		FailureAnalysis analysis = analyzeFailure(createFailure(JavaBeanBoundPropertiesConfiguration.class));
		assertThat(analysis).isNull();
	}

	private FatalBeanException createFailure(Class<?> config) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(config);
			context.refresh();
			return null;
		}
		catch (FatalBeanException ex) {
			return ex;
		}
	}

	private FailureAnalysis analyzeFailure(Exception failure) {
		assertThat(failure).isNotNull();
		FailureAnalysis analysis = this.analyzer.analyze(failure);
		if (analysis != null) {
			new LoggingFailureAnalysisReporter().report(analysis);
		}
		return analysis;
	}

	@ConstructorBinding
	@ConfigurationProperties("test")
	static class ConstructorBoundProperties {

		private final String name;

		ConstructorBoundProperties(String name) {
			this.name = name;
		}

		String getName() {
			return this.name;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(ConstructorBoundProperties.class)
	static class ShouldHaveUsedConstructorBindingPropertiesConfiguration {

	}

	@ConfigurationProperties("test")
	static class JavaBeanBoundProperties {

		private String name;

		JavaBeanBoundProperties(String dependency) {

		}

		String getName() {
			return this.name;
		}

		void setName(String name) {
			this.name = name;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(JavaBeanBoundProperties.class)
	static class JavaBeanBoundPropertiesConfiguration {

	}

}
