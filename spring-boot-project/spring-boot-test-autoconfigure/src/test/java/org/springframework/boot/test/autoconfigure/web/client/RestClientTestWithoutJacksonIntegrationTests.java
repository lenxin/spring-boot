package org.springframework.boot.test.autoconfigure.web.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.ClassUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Tests for {@link RestClientTest @RestClientTest} without Jackson.
 *

 */
@ClassPathExclusions("jackson-*.jar")
@RestClientTest(ExampleRestClient.class)
class RestClientTestWithoutJacksonIntegrationTests {

	@Autowired
	private MockRestServiceServer server;

	@Autowired
	private ExampleRestClient client;

	@Test
	void restClientTestCanBeUsedWhenJacksonIsNotOnTheClassPath() {
		ClassLoader classLoader = getClass().getClassLoader();
		assertThat(ClassUtils.isPresent("com.fasterxml.jackson.databind.Module", classLoader)).isFalse();
		this.server.expect(requestTo("/test")).andRespond(withSuccess("hello", MediaType.TEXT_HTML));
		assertThat(this.client.test()).isEqualTo("hello");
	}

}
