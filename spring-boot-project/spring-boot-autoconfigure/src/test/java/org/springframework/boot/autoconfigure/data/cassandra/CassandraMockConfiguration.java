package org.springframework.boot.autoconfigure.data.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.context.DriverContext;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Test configuration that mocks access to Cassandra.
 *

 */
@Configuration(proxyBeanMethods = false)
class CassandraMockConfiguration {

	final CodecRegistry codecRegistry = mock(CodecRegistry.class);

	@Bean
	CqlSession cqlSession() {
		DriverContext context = mock(DriverContext.class);
		given(context.getCodecRegistry()).willReturn(this.codecRegistry);
		CqlSession cqlSession = mock(CqlSession.class);
		given(cqlSession.getContext()).willReturn(context);
		return cqlSession;
	}

}
