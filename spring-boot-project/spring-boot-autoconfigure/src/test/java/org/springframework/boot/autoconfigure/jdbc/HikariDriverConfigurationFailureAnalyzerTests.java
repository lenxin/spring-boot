package org.springframework.boot.autoconfigure.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HikariDriverConfigurationFailureAnalyzer}.
 *

 */
class HikariDriverConfigurationFailureAnalyzerTests {

	@Test
	void failureAnalysisIsPerformed() {
		FailureAnalysis failureAnalysis = performAnalysis(TestConfiguration.class);
		assertThat(failureAnalysis).isNotNull();
		assertThat(failureAnalysis.getDescription()).isEqualTo(
				"Configuration of the Hikari connection pool failed: 'dataSourceClassName' is not supported.");
		assertThat(failureAnalysis.getAction()).contains("Spring Boot auto-configures only a driver");
	}

	@Test
	void unrelatedIllegalStateExceptionIsSkipped() {
		FailureAnalysis failureAnalysis = new HikariDriverConfigurationFailureAnalyzer()
				.analyze(new RuntimeException("foo", new IllegalStateException("bar")));
		assertThat(failureAnalysis).isNull();
	}

	private FailureAnalysis performAnalysis(Class<?> configuration) {
		BeanCreationException failure = createFailure(configuration);
		assertThat(failure).isNotNull();
		return new HikariDriverConfigurationFailureAnalyzer().analyze(failure);
	}

	private BeanCreationException createFailure(Class<?> configuration) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		TestPropertyValues.of("spring.datasource.type=" + HikariDataSource.class.getName(),
				"spring.datasource.hikari.data-source-class-name=com.example.Foo",
				"spring.datasource.initialization-mode=always").applyTo(context);
		context.register(configuration);
		try {
			context.refresh();
			context.close();
			return null;
		}
		catch (BeanCreationException ex) {
			return ex;
		}
	}

	@Configuration(proxyBeanMethods = false)
	@ImportAutoConfiguration(DataSourceAutoConfiguration.class)
	static class TestConfiguration {

	}

}
