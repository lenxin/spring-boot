package org.springframework.boot.test.autoconfigure.web.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Example web client used with {@link RestClientTest @RestClientTest} tests.
 *

 */
@Service
public class ExampleRestClient {

	private RestTemplate restTemplate;

	public ExampleRestClient(RestTemplateBuilder builder) {
		this.restTemplate = builder.rootUri("https://example.com").build();
	}

	protected RestTemplate getRestTemplate() {
		return this.restTemplate;
	}

	public String test() {
		return this.restTemplate.getForEntity("/test", String.class).getBody();
	}

	public void testPostWithBody(String body) {
		this.restTemplate.postForObject("/test", body, String.class);
	}

}
