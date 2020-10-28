package org.springframework.boot.test.autoconfigure.web.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Tests for {@link RestClientTest @RestClientTest} with two clients.
 *

 */
@RestClientTest({ ExampleRestClient.class, AnotherExampleRestClient.class })
class RestClientTestTwoComponentsIntegrationTests {

	@Autowired
	private ExampleRestClient client1;

	@Autowired
	private AnotherExampleRestClient client2;

	@Autowired
	private MockServerRestTemplateCustomizer customizer;

	@Autowired
	private MockRestServiceServer server;

	@Test
	void serverShouldNotWork() {
		assertThatIllegalStateException().isThrownBy(
				() -> this.server.expect(requestTo("/test")).andRespond(withSuccess("hello", MediaType.TEXT_HTML)))
				.withMessageContaining("Unable to use auto-configured");
	}

	@Test
	void client1RestCallViaCustomizer() {
		this.customizer.getServer(this.client1.getRestTemplate()).expect(requestTo("/test"))
				.andRespond(withSuccess("hello", MediaType.TEXT_HTML));
		assertThat(this.client1.test()).isEqualTo("hello");
	}

	@Test
	void client2RestCallViaCustomizer() {
		this.customizer.getServer(this.client2.getRestTemplate()).expect(requestTo("/test"))
				.andRespond(withSuccess("there", MediaType.TEXT_HTML));
		assertThat(this.client2.test()).isEqualTo("there");
	}

}
