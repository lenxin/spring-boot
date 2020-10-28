package org.springframework.boot.test.autoconfigure.orm.jpa;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Specific tests for {@link TestDatabaseAutoConfiguration} when no embedded database is
 * available.
 *


 */
@ClassPathExclusions({ "h2-*.jar", "hsqldb-*.jar", "derby-*.jar" })
class TestDatabaseAutoConfigurationNoEmbeddedTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(ExistingDataSourceConfiguration.class)
			.withConfiguration(AutoConfigurations.of(TestDatabaseAutoConfiguration.class));

	@Test
	void applyAnyReplace() {
		this.contextRunner.run((context) -> assertThat(context).getFailure().isInstanceOf(BeanCreationException.class)
				.hasMessageContaining("Failed to replace DataSource with an embedded database for tests.")
				.hasMessageContaining("If you want an embedded database please put a supported one on the classpath")
				.hasMessageContaining("or tune the replace attribute of @AutoConfigureTestDatabase."));
	}

	@Test
	void applyNoReplace() {
		this.contextRunner.withPropertyValues("spring.test.database.replace=NONE").run((context) -> {
			assertThat(context).hasSingleBean(DataSource.class);
			assertThat(context).getBean(DataSource.class).isSameAs(context.getBean("myCustomDataSource"));
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class ExistingDataSourceConfiguration {

		@Bean
		DataSource myCustomDataSource() {
			return mock(DataSource.class);
		}

	}

}
