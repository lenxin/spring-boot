package org.springframework.boot.test.autoconfigure.webservices.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.test.client.MockWebServiceServer;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link AutoConfigureMockWebServiceServer @AutoConfigureMockWebServiceServer}
 * with {@code enabled=false}.
 *

 */
@WebServiceClientTest
@AutoConfigureMockWebServiceServer(enabled = false)
class AutoConfigureMockWebServiceServerEnabledIntegrationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void mockWebServiceServerShouldNotBeRegistered() {
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.applicationContext.getBean(MockWebServiceServer.class));
	}

}
