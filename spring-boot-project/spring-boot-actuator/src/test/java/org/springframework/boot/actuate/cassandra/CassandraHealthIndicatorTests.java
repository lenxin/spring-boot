package org.springframework.boot.actuate.cassandra;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.cassandra.CassandraInternalException;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.CqlOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CassandraHealthIndicator}.
 *


 */
@Deprecated
class CassandraHealthIndicatorTests {

	@Test
	void createWhenCassandraOperationsIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CassandraHealthIndicator(null));
	}

	@Test
	void healthWithCassandraUp() {
		CassandraOperations cassandraOperations = mock(CassandraOperations.class);
		CqlOperations cqlOperations = mock(CqlOperations.class);
		CassandraHealthIndicator healthIndicator = new CassandraHealthIndicator(cassandraOperations);
		given(cassandraOperations.getCqlOperations()).willReturn(cqlOperations);
		given(cqlOperations.queryForObject(any(SimpleStatement.class), eq(String.class))).willReturn("1.0.0");
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails().get("version")).isEqualTo("1.0.0");
	}

	@Test
	void healthWithCassandraDown() {
		CassandraOperations cassandraOperations = mock(CassandraOperations.class);
		given(cassandraOperations.getCqlOperations()).willThrow(new CassandraInternalException("Connection failed"));
		CassandraHealthIndicator healthIndicator = new CassandraHealthIndicator(cassandraOperations);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
		assertThat(health.getDetails().get("error"))
				.isEqualTo(CassandraInternalException.class.getName() + ": Connection failed");
	}

}
