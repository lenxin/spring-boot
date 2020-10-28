package org.springframework.boot.test.autoconfigure.web.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Tests for {@link AutoConfigureTestDatabase @AutoConfigureTestDatabase} with
 * {@code registerRestTemplate=true}.
 *

 */
@SpringBootTest
@AutoConfigureWebClient(registerRestTemplate = true)
@AutoConfigureMockRestServiceServer
class AutoConfigureWebClientWithRestTemplateIntegrationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MockRestServiceServer server;

	@Test
	void restTemplateTest() {
		this.server.expect(requestTo("/test")).andRespond(withSuccess("hello", MediaType.TEXT_HTML));
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/test", String.class);
		assertThat(entity.getBody()).isEqualTo("hello");
	}

	@Configuration(proxyBeanMethods = false)
	@EnableAutoConfiguration(exclude = CassandraAutoConfiguration.class)
	static class Config {

	}

}
