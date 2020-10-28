package org.springframework.boot.test.autoconfigure.web.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for
 * {@link AutoConfigureMockRestServiceServer @AutoConfigureMockRestServiceServer} with
 * {@code enabled=false}.
 *

 */
@RestClientTest
@AutoConfigureMockRestServiceServer(enabled = false)
class AutoConfigureMockRestServiceServerEnabledFalseIntegrationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void mockServerRestTemplateCustomizerShouldNotBeRegistered() {
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.applicationContext.getBean(MockServerRestTemplateCustomizer.class));
	}

}
