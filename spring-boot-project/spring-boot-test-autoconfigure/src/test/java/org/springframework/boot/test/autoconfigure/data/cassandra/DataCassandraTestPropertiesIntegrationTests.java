package org.springframework.boot.test.autoconfigure.data.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.context.DriverContext;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for the {@link DataCassandraTest#properties properties} attribute of
 * {@link DataCassandraTest @DataCassandraTest}.
 *

 */
@DataCassandraTest(properties = "spring.profiles.active=test")
class DataCassandraTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

	@TestConfiguration(proxyBeanMethods = false)
	static class CassandraMockConfiguration {

		@Bean
		CqlSession cqlSession() {
			DriverContext context = mock(DriverContext.class);
			CodecRegistry codecRegistry = mock(CodecRegistry.class);
			given(context.getCodecRegistry()).willReturn(codecRegistry);
			CqlSession cqlSession = mock(CqlSession.class);
			given(cqlSession.getContext()).willReturn(context);
			return cqlSession;
		}

	}

}
