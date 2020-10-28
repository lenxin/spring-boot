package org.springframework.boot.devtools.autoconfigure;

import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link DevToolsDataSourceAutoConfiguration} with an embedded data source.
 *

 */
@ClassPathExclusions("HikariCP-*.jar")
class DevToolsEmbeddedDataSourceAutoConfigurationTests extends AbstractDevToolsDataSourceAutoConfigurationTests {

	@Test
	void autoConfiguredDataSourceIsNotShutdown() throws SQLException {
		ConfigurableApplicationContext context = createContext(DataSourceAutoConfiguration.class,
				DataSourceSpyConfiguration.class);
		Statement statement = configureDataSourceBehavior(context.getBean(DataSource.class));
		context.close();
		verify(statement, never()).execute("SHUTDOWN");
	}

}
