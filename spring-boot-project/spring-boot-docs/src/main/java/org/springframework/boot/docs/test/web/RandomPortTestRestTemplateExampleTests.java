package org.springframework.boot.docs.test.web;

// tag::test-random-port[]

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RandomPortTestRestTemplateExampleTests {

	@Test
	void exampleTest(@Autowired TestRestTemplate restTemplate) {
		String body = restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello World");
	}

}
// end::test-random-port[]
