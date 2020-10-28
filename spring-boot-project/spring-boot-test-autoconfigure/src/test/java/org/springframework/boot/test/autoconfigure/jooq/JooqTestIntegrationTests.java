package org.springframework.boot.test.autoconfigure.jooq;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.ExampleComponent;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.boot.test.autoconfigure.AutoConfigurationImportedCondition.importedAutoConfiguration;

/**
 * Integration tests for {@link JooqTest @JooqTest}.
 *

 */
@JooqTest
class JooqTestIntegrationTests {

	@Autowired
	private DSLContext dsl;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void testDSLContext() {
		assertThat(this.dsl.selectCount().from("INFORMATION_SCHEMA.TABLES").fetchOne(0, Integer.class))
				.isGreaterThan(0);
	}

	@Test
	void useDefinedDataSource() throws Exception {
		String product = this.dataSource.getConnection().getMetaData().getDatabaseProductName();
		assertThat(product).startsWith("HSQL");
		assertThat(this.dsl.configuration().dialect()).isEqualTo(SQLDialect.HSQLDB);
	}

	@Test
	void didNotInjectExampleComponent() {
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.applicationContext.getBean(ExampleComponent.class));
	}

	@Test
	void flywayAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(FlywayAutoConfiguration.class));
	}

	@Test
	void liquibaseAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(LiquibaseAutoConfiguration.class));
	}

	@Test
	void cacheAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(CacheAutoConfiguration.class));
	}

}
