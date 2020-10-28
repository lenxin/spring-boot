package org.springframework.boot.test.autoconfigure.web.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * A second example web client used with {@link RestClientTest @RestClientTest} tests.
 *

 */
@Service
public class AnotherExampleRestClient {

	private final RestTemplate restTemplate;

	public AnotherExampleRestClient(RestTemplateBuilder builder) {
		this.restTemplate = builder.rootUri("https://example.com").build();
	}

	protected RestTemplate getRestTemplate() {
		return this.restTemplate;
	}

	public String test() {
		return this.restTemplate.getForEntity("/test", String.class).getBody();
	}

}
