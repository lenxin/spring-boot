package org.springframework.boot.actuate.metrics.web.client;

import java.io.IOException;

import io.micrometer.core.instrument.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link RestTemplateExchangeTags}.
 *


 */
class RestTemplateExchangeTagsTests {

	@Test
	void outcomeTagIsUnknownWhenResponseIsNull() {
		Tag tag = RestTemplateExchangeTags.outcome(null);
		assertThat(tag.getValue()).isEqualTo("UNKNOWN");
	}

	@Test
	void outcomeTagIsInformationalWhenResponseIs1xx() {
		ClientHttpResponse response = new MockClientHttpResponse("foo".getBytes(), HttpStatus.CONTINUE);
		Tag tag = RestTemplateExchangeTags.outcome(response);
		assertThat(tag.getValue()).isEqualTo("INFORMATIONAL");
	}

	@Test
	void outcomeTagIsSuccessWhenResponseIs2xx() {
		ClientHttpResponse response = new MockClientHttpResponse("foo".getBytes(), HttpStatus.OK);
		Tag tag = RestTemplateExchangeTags.outcome(response);
		assertThat(tag.getValue()).isEqualTo("SUCCESS");
	}

	@Test
	void outcomeTagIsRedirectionWhenResponseIs3xx() {
		ClientHttpResponse response = new MockClientHttpResponse("foo".getBytes(), HttpStatus.MOVED_PERMANENTLY);
		Tag tag = RestTemplateExchangeTags.outcome(response);
		assertThat(tag.getValue()).isEqualTo("REDIRECTION");
	}

	@Test
	void outcomeTagIsClientErrorWhenResponseIs4xx() {
		ClientHttpResponse response = new MockClientHttpResponse("foo".getBytes(), HttpStatus.BAD_REQUEST);
		Tag tag = RestTemplateExchangeTags.outcome(response);
		assertThat(tag.getValue()).isEqualTo("CLIENT_ERROR");
	}

	@Test
	void outcomeTagIsServerErrorWhenResponseIs5xx() {
		ClientHttpResponse response = new MockClientHttpResponse("foo".getBytes(), HttpStatus.BAD_GATEWAY);
		Tag tag = RestTemplateExchangeTags.outcome(response);
		assertThat(tag.getValue()).isEqualTo("SERVER_ERROR");
	}

	@Test
	void outcomeTagIsUnknownWhenResponseThrowsIOException() throws Exception {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		given(response.getRawStatusCode()).willThrow(IOException.class);
		Tag tag = RestTemplateExchangeTags.outcome(response);
		assertThat(tag.getValue()).isEqualTo("UNKNOWN");
	}

	@Test
	void outcomeTagIsClientErrorWhenResponseIsNonStandardInClientSeries() throws IOException {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		given(response.getRawStatusCode()).willReturn(490);
		Tag tag = RestTemplateExchangeTags.outcome(response);
		assertThat(tag.getValue()).isEqualTo("CLIENT_ERROR");
	}

	@Test
	void outcomeTagIsUnknownWhenResponseStatusIsInUnknownSeries() throws IOException {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		given(response.getRawStatusCode()).willReturn(701);
		Tag tag = RestTemplateExchangeTags.outcome(response);
		assertThat(tag.getValue()).isEqualTo("UNKNOWN");
	}

}
