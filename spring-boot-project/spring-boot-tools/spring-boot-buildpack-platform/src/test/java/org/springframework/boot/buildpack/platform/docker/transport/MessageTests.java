package org.springframework.boot.buildpack.platform.docker.transport;

import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.json.AbstractJsonTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Message}.
 *

 */
class MessageTests extends AbstractJsonTests {

	@Test
	void readValueDeserializesJson() throws Exception {
		Message message = getObjectMapper().readValue(getContent("message.json"), Message.class);
		assertThat(message.getMessage()).isEqualTo("test message");
	}

	@Test
	void toStringHasErrorDetails() throws Exception {
		Message errors = getObjectMapper().readValue(getContent("message.json"), Message.class);
		assertThat(errors.toString()).isEqualTo("test message");
	}

}
