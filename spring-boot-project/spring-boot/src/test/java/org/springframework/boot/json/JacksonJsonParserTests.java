package org.springframework.boot.json;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link JacksonJsonParser}.
 *


 */
class JacksonJsonParserTests extends AbstractJsonParserTests {

	@Override
	protected JsonParser getParser() {
		return new JacksonJsonParser();
	}

	@Test
	@SuppressWarnings("unchecked")
	void instanceWithSpecificObjectMapper() throws IOException {
		ObjectMapper objectMapper = spy(new ObjectMapper());
		new JacksonJsonParser(objectMapper).parseMap("{}");
		verify(objectMapper).readValue(eq("{}"), any(TypeReference.class));
	}

}
