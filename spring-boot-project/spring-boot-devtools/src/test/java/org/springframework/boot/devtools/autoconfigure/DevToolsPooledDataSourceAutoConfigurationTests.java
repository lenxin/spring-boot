package org.springframework.boot.devtools.autoconfigure;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link DevToolsDataSourceAutoConfiguration} with a pooled data source.
 *

 */
class DevToolsPooledDataSourceAutoConfigurationTests extends AbstractDevToolsDataSourceAutoConfigurationTests {

	@BeforeEach
	void before(@TempDir File tempDir) throws IOException {
		System.setProperty("derby.stream.error.file", new File(tempDir, "derby.log").getAbsolutePath());
	}

	@AfterEach
	void after() {
		System.clearProperty("derby.stream.error.file");
	}

	@Test
	void autoConfiguredInMemoryDataSourceIsShutdown() throws Exception {
		ConfigurableApplicationContext context = getContext(
				() -> createContext(DataSourceAutoConfiguration.class, DataSourceSpyConfiguration.class));
		Statement statement = configureDataSourceBehavior(context.getBean(DataSource.class));
		context.close();
		verify(statement).execute("SHUTDOWN");
	}

	@Test
	void autoConfiguredExternalDataSourceIsNotShutdown() throws Exception {
		ConfigurableApplicationContext context = getContext(() -> createContext("org.postgresql.Driver",
				DataSourceAutoConfiguration.class, DataSourceSpyConfiguration.class));
		Statement statement = configureDataSourceBehavior(context.getBean(DataSource.class));
		context.close();
		verify(statement, never()).execute("SHUTDOWN");
	}

	@Test
	void h2ServerIsNotShutdown() throws Exception {
		ConfigurableApplicationContext context = getContext(() -> createContext("org.h2.Driver",
				"jdbc:h2:hsql://localhost", DataSourceAutoConfiguration.class, DataSourceSpyConfiguration.class));
		Statement statement = configureDataSourceBehavior(context.getBean(DataSource.class));
		context.close();
		verify(statement, never()).execute("SHUTDOWN");
	}

	@Test
	void inMemoryH2IsShutdown() throws Exception {
		ConfigurableApplicationContext context = getContext(() -> createContext("org.h2.Driver", "jdbc:h2:mem:test",
				DataSourceAutoConfiguration.class, DataSourceSpyConfiguration.class));
		Statement statement = configureDataSourceBehavior(context.getBean(DataSource.class));
		context.close();
		verify(statement, times(1)).execute("SHUTDOWN");
	}

	@Test
	void hsqlServerIsNotShutdown() throws Exception {
		ConfigurableApplicationContext context = getContext(() -> createContext("org.hsqldb.jdbcDriver",
				"jdbc:hsqldb:hsql://localhost", DataSourceAutoConfiguration.class, DataSourceSpyConfiguration.class));
		Statement statement = configureDataSourceBehavior(context.getBean(DataSource.class));
		context.close();
		verify(statement, never()).execute("SHUTDOWN");
	}

	@Test
	void inMemoryHsqlIsShutdown() throws Exception {
		ConfigurableApplicationContext context = getContext(() -> createContext("org.hsqldb.jdbcDriver",
				"jdbc:hsqldb:mem:test", DataSourceAutoConfiguration.class, DataSourceSpyConfiguration.class));
		Statement statement = configureDataSourceBehavior(context.getBean(DataSource.class));
		context.close();
		verify(statement, times(1)).execute("SHUTDOWN");
	}

	@Test
	void derbyClientIsNotShutdown() throws Exception {
		ConfigurableApplicationContext context = getContext(() -> createContext("org.apache.derby.jdbc.ClientDriver",
				"jdbc:derby://localhost", DataSourceAutoConfiguration.class, DataSourceSpyConfiguration.class));
		Statement statement = configureDataSourceBehavior(context.getBean(DataSource.class));
		context.close();
		verify(statement, never()).execute("SHUTDOWN");
	}

	@Test
	void inMemoryDerbyIsShutdown() throws Exception {
		ConfigurableApplicationContext context = getContext(
				() -> createContext("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:memory:test;create=true",
						DataSourceAutoConfiguration.class, DataSourceSpyConfiguration.class));
		HikariDataSource dataSource = context.getBean(HikariDataSource.class);
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("SELECT 1 FROM SYSIBM.SYSDUMMY1");
		HikariPoolMXBean pool = dataSource.getHikariPoolMXBean();
		// Prevent a race between Hikari's initialization and Derby shutdown
		Awaitility.await().atMost(Duration.ofSeconds(30)).until(pool::getIdleConnections,
				(idle) -> idle == dataSource.getMinimumIdle());
		context.close();
		// Connect should fail as DB no longer exists
		assertThatExceptionOfType(SQLException.class)
				.isThrownBy(() -> new EmbeddedDriver().connect("jdbc:derby:memory:test", new Properties()))
				.satisfies((ex) -> assertThat(ex.getSQLState()).isEqualTo("XJ004"));
		// Shut Derby down fully so that it closes its log file
		assertThatExceptionOfType(SQLException.class)
				.isThrownBy(() -> new EmbeddedDriver().connect("jdbc:derby:;shutdown=true", new Properties()));
	}

}
