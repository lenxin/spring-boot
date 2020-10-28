package org.springframework.boot.actuate.web.trace.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TraceableHttpServletRequest}.
 *

 */
class TraceableHttpServletRequestTests {

	private MockHttpServletRequest request;

	@BeforeEach
	void setup() {
		this.request = new MockHttpServletRequest("GET", "/script");
	}

	@Test
	void getUriWithoutQueryStringShouldReturnUri() {
		validate("http://localhost/script");
	}

	@Test
	void getUriShouldReturnUriWithQueryString() {
		this.request.setQueryString("a=b");
		validate("http://localhost/script?a=b");
	}

	@Test
	void getUriWithSpecialCharactersInQueryStringShouldEncode() {
		this.request.setQueryString("a=${b}");
		validate("http://localhost/script?a=$%7Bb%7D");
	}

	@Test
	void getUriWithSpecialCharactersEncodedShouldNotDoubleEncode() {
		this.request.setQueryString("a=$%7Bb%7D");
		validate("http://localhost/script?a=$%7Bb%7D");
	}

	private void validate(String expectedUri) {
		TraceableHttpServletRequest trace = new TraceableHttpServletRequest(this.request);
		assertThat(trace.getUri().toString()).isEqualTo(expectedUri);
	}

}
