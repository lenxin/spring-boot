package org.springframework.boot.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Helper base class for {@link JsonSerializer} implementations that serialize objects.
 *
 * @param <T> the supported object type

 * @since 1.4.0
 * @see JsonObjectDeserializer
 */
public abstract class JsonObjectSerializer<T> extends JsonSerializer<T> {

	@Override
	public final void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		try {
			jgen.writeStartObject();
			serializeObject(value, jgen, provider);
			jgen.writeEndObject();
		}
		catch (Exception ex) {
			if (ex instanceof IOException) {
				throw (IOException) ex;
			}
			throw new JsonMappingException(jgen, "Object serialize error", ex);
		}
	}

	/**
	 * Serialize JSON content into the value type this serializer handles.
	 * @param value the source value
	 * @param jgen the JSON generator
	 * @param provider the serializer provider
	 * @throws IOException on error
	 */
	protected abstract void serializeObject(T value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException;

}
