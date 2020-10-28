package org.springframework.boot.docs.jdbc;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link BasicDataSourceExample}.
 *

 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "app.datasource.jdbcUrl=jdbc:h2:mem:basic;DB_CLOSE_DELAY=-1")
@Import(BasicDataSourceExample.BasicDataSourceConfiguration.class)
class BasicDataSourceExampleTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void validateConfiguration() throws SQLException {
		assertThat(this.context.getBeansOfType(DataSource.class)).hasSize(1);
		DataSource dataSource = this.context.getBean(DataSource.class);
		assertThat(dataSource.getConnection().getMetaData().getURL()).isEqualTo("jdbc:h2:mem:basic");
	}

}
