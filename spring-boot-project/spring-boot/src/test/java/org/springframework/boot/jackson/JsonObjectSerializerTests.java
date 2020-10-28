package org.springframework.boot.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import org.springframework.boot.jackson.NameAndAgeJsonComponent.Serializer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JsonObjectSerializer}.
 *

 */
class JsonObjectSerializerTests {

	@Test
	void serializeObjectShouldWriteJson() throws Exception {
		Serializer serializer = new NameAndAgeJsonComponent.Serializer();
		SimpleModule module = new SimpleModule();
		module.addSerializer(NameAndAge.class, serializer);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(module);
		String json = mapper.writeValueAsString(new NameAndAge("spring", 100));
		assertThat(json).isEqualToIgnoringWhitespace("{\"name\":\"spring\",\"age\":100}");
	}

}
