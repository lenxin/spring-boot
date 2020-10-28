package org.springframework.boot.docs.jdbc;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SimpleTwoDataSourcesExample}.
 *

 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = { "app.datasource.second.url=jdbc:h2:mem:bar;DB_CLOSE_DELAY=-1",
		"app.datasource.second.max-total=42" })
@Import(SimpleTwoDataSourcesExample.SimpleDataSourcesConfiguration.class)
class SimpleTwoDataSourcesExampleTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void validateConfiguration() throws SQLException {
		assertThat(this.context.getBeansOfType(DataSource.class)).hasSize(2);
		DataSource dataSource = this.context.getBean(DataSource.class);
		assertThat(this.context.getBean("firstDataSource")).isSameAs(dataSource);
		assertThat(dataSource.getConnection().getMetaData().getURL()).startsWith("jdbc:h2:mem:");
		BasicDataSource secondDataSource = this.context.getBean("secondDataSource", BasicDataSource.class);
		assertThat(secondDataSource.getUrl()).isEqualTo("jdbc:h2:mem:bar;DB_CLOSE_DELAY=-1");
		assertThat(secondDataSource.getMaxTotal()).isEqualTo(42);
	}

}
