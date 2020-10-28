package org.springframework.boot.docs.web.client;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example integration test that uses {@link TestRestTemplate}.
 *

 */
// tag::test[]
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SampleWebClientTests {

	@Autowired
	private TestRestTemplate template;

	@Test
	void testRequest() {
		HttpHeaders headers = this.template.getForEntity("/example", String.class).getHeaders();
		assertThat(headers.getLocation()).hasHost("other.example.com");
	}

	@TestConfiguration(proxyBeanMethods = false)
	static class Config {

		@Bean
		RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(1))
					.setReadTimeout(Duration.ofSeconds(1));
		}

	}

}
// end::test[]
