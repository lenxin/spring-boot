package org.springframework.boot.jdbc.metadata;

import org.junit.jupiter.api.Test;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Abstract base class for {@link DataSourcePoolMetadata} tests.
 *
 * @param <D> the data source pool metadata type


 */
abstract class AbstractDataSourcePoolMetadataTests<D extends AbstractDataSourcePoolMetadata<?>> {

	/**
	 * Return a data source metadata instance with a min size of 0 and max size of 2. Idle
	 * connections are not reclaimed immediately.
	 * @return the data source metadata
	 */
	protected abstract D getDataSourceMetadata();

	@Test
	void getMaxPoolSize() {
		assertThat(getDataSourceMetadata().getMax()).isEqualTo(2);
	}

	@Test
	void getMinPoolSize() {
		assertThat(getDataSourceMetadata().getMin()).isEqualTo(0);
	}

	@Test
	void getPoolSizeNoConnection() {
		// Make sure the pool is initialized
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSourceMetadata().getDataSource());
		jdbcTemplate.execute((ConnectionCallback<Void>) (connection) -> null);
		assertThat(getDataSourceMetadata().getActive()).isEqualTo(0);
		assertThat(getDataSourceMetadata().getUsage()).isEqualTo(0f);
	}

	@Test
	void getPoolSizeOneConnection() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSourceMetadata().getDataSource());
		jdbcTemplate.execute((ConnectionCallback<Void>) (connection) -> {
			assertThat(getDataSourceMetadata().getActive()).isEqualTo(1);
			assertThat(getDataSourceMetadata().getUsage()).isEqualTo(0.5f);
			return null;
		});
	}

	@Test
	void getIdle() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSourceMetadata().getDataSource());
		jdbcTemplate.execute((ConnectionCallback<Void>) (connection) -> null);
		assertThat(getDataSourceMetadata().getIdle()).isEqualTo(1);
	}

	@Test
	void getPoolSizeTwoConnections() {
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSourceMetadata().getDataSource());
		jdbcTemplate.execute((ConnectionCallback<Void>) (connection) -> {
			jdbcTemplate.execute((ConnectionCallback<Void>) (connection1) -> {
				assertThat(getDataSourceMetadata().getActive()).isEqualTo(2);
				assertThat(getDataSourceMetadata().getUsage()).isEqualTo(1.0f);
				return null;
			});
			return null;
		});
	}

	@Test
	abstract void getValidationQuery() throws Exception;

	@Test
	abstract void getDefaultAutoCommit() throws Exception;

	protected DataSourceBuilder<?> initializeBuilder() {
		return DataSourceBuilder.create().driverClassName("org.hsqldb.jdbc.JDBCDriver").url("jdbc:hsqldb:mem:test")
				.username("sa");
	}

}
