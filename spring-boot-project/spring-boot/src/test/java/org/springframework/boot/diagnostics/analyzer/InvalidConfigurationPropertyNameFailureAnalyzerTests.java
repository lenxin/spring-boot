package org.springframework.boot.diagnostics.analyzer;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InvalidConfigurationPropertyNameFailureAnalyzer}.
 *

 */
class InvalidConfigurationPropertyNameFailureAnalyzerTests {

	private InvalidConfigurationPropertyNameFailureAnalyzer analyzer = new InvalidConfigurationPropertyNameFailureAnalyzer();

	@Test
	void analysisWhenRootCauseIsBeanCreationFailureShouldContainBeanName() {
		BeanCreationException failure = createFailure(InvalidPrefixConfiguration.class);
		FailureAnalysis analysis = this.analyzer.analyze(failure);
		assertThat(analysis.getDescription())
				.contains(String.format("%n    Invalid characters: %s%n    Bean: %s%n    Reason: %s", "'F', 'P'",
						"invalidPrefixProperties", "Canonical names should be kebab-case ('-' separated), "
								+ "lowercase alpha-numeric characters and must start with a letter"));
	}

	private BeanCreationException createFailure(Class<?> configuration) {
		try {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
			context.register(configuration);
			context.refresh();
			context.close();
			return null;
		}
		catch (BeanCreationException ex) {
			return ex;
		}
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(InvalidPrefixProperties.class)
	static class InvalidPrefixConfiguration {

		@Bean(name = "invalidPrefixProperties")
		InvalidPrefixProperties invalidPrefixProperties() {
			return new InvalidPrefixProperties();
		}

	}

	@ConfigurationProperties("FooPrefix")
	static class InvalidPrefixProperties {

		private String value;

		String getValue() {
			return this.value;
		}

		void setValue(String value) {
			this.value = value;
		}

	}

}
