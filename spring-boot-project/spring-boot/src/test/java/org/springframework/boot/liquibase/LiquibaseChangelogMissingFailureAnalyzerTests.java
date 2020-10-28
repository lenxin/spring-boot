package org.springframework.boot.liquibase;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LiquibaseChangelogMissingFailureAnalyzer}
 *

 */
@ClassPathExclusions("derby-*")
class LiquibaseChangelogMissingFailureAnalyzerTests {

	@Test
	void changelogParseExceptionDueToChangelogNotPresent() {
		FailureAnalysis analysis = performAnalysis();
		assertThat(analysis.getDescription())
				.isEqualTo("Liquibase failed to start because no changelog could be found at '"
						+ "classpath:/db/changelog/db.changelog-master.yaml'.");
		assertThat(analysis.getAction())
				.isEqualTo("Make sure a Liquibase changelog is present at the configured path.");
	}

	private FailureAnalysis performAnalysis() {
		BeanCreationException failure = createFailure();
		assertThat(failure).isNotNull();
		return new LiquibaseChangelogMissingFailureAnalyzer().analyze(failure);
	}

	private BeanCreationException createFailure() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				LiquibaseConfiguration.class)) {
			return null;
		}
		catch (BeanCreationException ex) {
			return ex;
		}
	}

	@Configuration(proxyBeanMethods = false)
	static class LiquibaseConfiguration {

		@Bean
		DataSource dataSource() {
			return DataSourceBuilder.create().url("jdbc:hsqldb:mem:test").username("sa").build();
		}

		@Bean
		SpringLiquibase springLiquibase(DataSource dataSource) {
			SpringLiquibase liquibase = new SpringLiquibase();
			liquibase.setChangeLog("classpath:/db/changelog/db.changelog-master.yaml");
			liquibase.setDataSource(dataSource);
			return liquibase;
		}

	}

}
