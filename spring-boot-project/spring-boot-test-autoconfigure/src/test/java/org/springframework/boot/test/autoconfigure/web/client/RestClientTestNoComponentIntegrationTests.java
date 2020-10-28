package org.springframework.boot.test.autoconfigure.web.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Tests for {@link RestClientTest @RestClientTest} with no specific client.
 *

 */
@RestClientTest
class RestClientTestNoComponentIntegrationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private MockRestServiceServer server;

	@Test
	void exampleRestClientIsNotInjected() {
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.applicationContext.getBean(ExampleRestClient.class));
	}

	@Test
	void examplePropertiesIsNotInjected() {
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.applicationContext.getBean(ExampleProperties.class));
	}

	@Test
	void manuallyCreateBean() {
		ExampleRestClient client = new ExampleRestClient(this.restTemplateBuilder);
		this.server.expect(requestTo("/test")).andRespond(withSuccess("hello", MediaType.TEXT_HTML));
		assertThat(client.test()).isEqualTo("hello");
	}

}
