package org.springframework.boot.autoconfigure.jdbc;

import org.junit.jupiter.api.Test;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.context.FilteredClassLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link DataSourceProperties}.
 *




 */
class DataSourcePropertiesTests {

	@Test
	void determineDriver() {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setUrl("jdbc:mysql://mydb");
		assertThat(properties.getDriverClassName()).isNull();
		assertThat(properties.determineDriverClassName()).isEqualTo("com.mysql.cj.jdbc.Driver");
	}

	@Test
	void determineDriverWithExplicitConfig() {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setUrl("jdbc:mysql://mydb");
		properties.setDriverClassName("org.hsqldb.jdbcDriver");
		assertThat(properties.getDriverClassName()).isEqualTo("org.hsqldb.jdbcDriver");
		assertThat(properties.determineDriverClassName()).isEqualTo("org.hsqldb.jdbcDriver");
	}

	@Test
	void determineUrlWithoutGenerateUniqueName() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setGenerateUniqueName(false);
		properties.afterPropertiesSet();
		assertThat(properties.getUrl()).isNull();
		assertThat(properties.determineUrl()).isEqualTo(EmbeddedDatabaseConnection.H2.getUrl("testdb"));
	}

	@Test
	void determineUrlWithNoEmbeddedSupport() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setBeanClassLoader(new FilteredClassLoader("org.h2", "org.apache.derby", "org.hsqldb"));
		properties.afterPropertiesSet();
		assertThatExceptionOfType(DataSourceProperties.DataSourceBeanCreationException.class)
				.isThrownBy(properties::determineUrl).withMessageContaining("Failed to determine suitable jdbc url");
	}

	@Test
	void determineUrlWithExplicitConfig() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setUrl("jdbc:mysql://mydb");
		properties.afterPropertiesSet();
		assertThat(properties.getUrl()).isEqualTo("jdbc:mysql://mydb");
		assertThat(properties.determineUrl()).isEqualTo("jdbc:mysql://mydb");
	}

	@Test
	void determineUrlWithGenerateUniqueName() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.afterPropertiesSet();
		assertThat(properties.determineUrl()).isEqualTo(properties.determineUrl());

		DataSourceProperties properties2 = new DataSourceProperties();
		properties2.setGenerateUniqueName(true);
		properties2.afterPropertiesSet();
		assertThat(properties.determineUrl()).isNotEqualTo(properties2.determineUrl());
	}

	@Test
	void determineUsername() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.afterPropertiesSet();
		assertThat(properties.getUsername()).isNull();
		assertThat(properties.determineUsername()).isEqualTo("sa");
	}

	@Test
	void determineUsernameWhenEmpty() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setUsername("");
		properties.afterPropertiesSet();
		assertThat(properties.getUsername()).isEqualTo("");
		assertThat(properties.determineUsername()).isEqualTo("sa");
	}

	@Test
	void determineUsernameWhenNull() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setUsername(null);
		properties.afterPropertiesSet();
		assertThat(properties.getUsername()).isNull();
		assertThat(properties.determineUsername()).isEqualTo("sa");
	}

	@Test
	void determineUsernameWithExplicitConfig() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setUsername("foo");
		properties.afterPropertiesSet();
		assertThat(properties.getUsername()).isEqualTo("foo");
		assertThat(properties.determineUsername()).isEqualTo("foo");
	}

	@Test
	void determineUsernameWithNonEmbeddedUrl() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setUrl("jdbc:h2:~/test");
		properties.afterPropertiesSet();
		assertThat(properties.getPassword()).isNull();
		assertThat(properties.determineUsername()).isNull();
	}

	@Test
	void determinePassword() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.afterPropertiesSet();
		assertThat(properties.getPassword()).isNull();
		assertThat(properties.determinePassword()).isEqualTo("");
	}

	@Test
	void determinePasswordWithExplicitConfig() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setPassword("bar");
		properties.afterPropertiesSet();
		assertThat(properties.getPassword()).isEqualTo("bar");
		assertThat(properties.determinePassword()).isEqualTo("bar");
	}

	@Test
	void determinePasswordWithNonEmbeddedUrl() throws Exception {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setUrl("jdbc:h2:~/test");
		properties.afterPropertiesSet();
		assertThat(properties.getPassword()).isNull();
		assertThat(properties.determinePassword()).isNull();
	}

	@Test
	void determineCredentialsForSchemaScripts() {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setSchemaUsername("foo");
		properties.setSchemaPassword("bar");
		assertThat(properties.getSchemaUsername()).isEqualTo("foo");
		assertThat(properties.getSchemaPassword()).isEqualTo("bar");
	}

	@Test
	void determineCredentialsForDataScripts() {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setDataUsername("foo");
		properties.setDataPassword("bar");
		assertThat(properties.getDataUsername()).isEqualTo("foo");
		assertThat(properties.getDataPassword()).isEqualTo("bar");
	}

}
