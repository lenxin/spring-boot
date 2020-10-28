package org.springframework.boot.test.autoconfigure.json.app;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

/**
 * Example {@link JsonComponent @JsonComponent} for use with {@link JsonTest @JsonTest}
 * tests.
 *

 */
@JsonComponent
public class ExampleJsonComponent {

	static class Serializer extends JsonObjectSerializer<ExampleCustomObject> {

		@Override
		protected void serializeObject(ExampleCustomObject value, JsonGenerator jgen, SerializerProvider provider)
				throws IOException {
			jgen.writeStringField("value", value.toString());
		}

	}

	static class Deserializer extends JsonObjectDeserializer<ExampleCustomObject> {

		@Override
		protected ExampleCustomObject deserializeObject(JsonParser jsonParser, DeserializationContext context,
				ObjectCodec codec, JsonNode tree) throws IOException {
			return new ExampleCustomObject(nullSafeValue(tree.get("value"), String.class));
		}

	}

}
