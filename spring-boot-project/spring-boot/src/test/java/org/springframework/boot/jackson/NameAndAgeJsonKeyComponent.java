package org.springframework.boot.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Sample {@link JsonComponent @JsonComponent} used for tests.
 *

 */
@JsonComponent(type = NameAndAge.class, scope = JsonComponent.Scope.KEYS)
public class NameAndAgeJsonKeyComponent {

	static class Serializer extends JsonSerializer<NameAndAge> {

		@Override
		public void serialize(NameAndAge value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
			jgen.writeFieldName(value.asKey());
		}

	}

	static class Deserializer extends KeyDeserializer {

		@Override
		public NameAndAge deserializeKey(String key, DeserializationContext ctxt) throws IOException {
			String[] keys = key.split("is");
			return new NameAndAge(keys[0].trim(), Integer.parseInt(keys[1].trim()));
		}

	}

}
