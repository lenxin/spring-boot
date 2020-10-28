package org.springframework.boot.test.autoconfigure.web.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Tests for {@link RestClientTest @RestClientTest} gets reset after test methods.
 *

 */
@RestClientTest(ExampleRestClient.class)
class RestClientRestIntegrationTests {

	@Autowired
	private MockRestServiceServer server;

	@Autowired
	private ExampleRestClient client;

	@Test
	void mockServerCall1() {
		this.server.expect(requestTo("/test")).andRespond(withSuccess("1", MediaType.TEXT_HTML));
		assertThat(this.client.test()).isEqualTo("1");
	}

	@Test
	void mockServerCall2() {
		this.server.expect(requestTo("/test")).andRespond(withSuccess("2", MediaType.TEXT_HTML));
		assertThat(this.client.test()).isEqualTo("2");
	}

	@Test
	void mockServerCallWithContent() {
		this.server.expect(requestTo("/test")).andExpect(content().string("test"))
				.andRespond(withSuccess("1", MediaType.TEXT_HTML));
		this.client.testPostWithBody("test");
	}

}
