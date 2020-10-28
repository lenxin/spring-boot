package org.springframework.boot.docs.jdbc;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link SimpleDataSourceExample}.
 *

 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = { "app.datasource.jdbc-url=jdbc:h2:mem:simple;DB_CLOSE_DELAY=-1",
		"app.datasource.maximum-pool-size=42" })
@Import(SimpleDataSourceExample.SimpleDataSourceConfiguration.class)
class SimpleDataSourceExampleTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void validateConfiguration() throws SQLException {
		assertThat(this.context.getBeansOfType(DataSource.class)).hasSize(1);
		HikariDataSource dataSource = this.context.getBean(HikariDataSource.class);
		assertThat(dataSource.getConnection().getMetaData().getURL()).isEqualTo("jdbc:h2:mem:simple");
		assertThat(dataSource.getMaximumPoolSize()).isEqualTo(42);
	}

}
