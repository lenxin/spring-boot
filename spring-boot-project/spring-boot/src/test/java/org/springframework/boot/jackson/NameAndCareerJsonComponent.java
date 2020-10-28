package org.springframework.boot.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Sample {@link JsonComponent @JsonComponent} used for tests.
 *

 */
@JsonComponent(type = NameAndCareer.class)
public class NameAndCareerJsonComponent {

	static class Serializer extends JsonObjectSerializer<Name> {

		@Override
		protected void serializeObject(Name value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			jgen.writeStringField("name", value.getName());
		}

	}

	static class Deserializer extends JsonObjectDeserializer<Name> {

		@Override
		protected Name deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec,
				JsonNode tree) throws IOException {
			String name = nullSafeValue(tree.get("name"), String.class);
			String career = nullSafeValue(tree.get("career"), String.class);
			return new NameAndCareer(name, career);
		}

	}

}
