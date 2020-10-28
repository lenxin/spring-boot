package org.springframework.boot.json;

import org.springframework.util.ClassUtils;

/**
 * Factory to create a {@link JsonParser}.
 *

 * @since 1.0.0
 * @see JacksonJsonParser
 * @see GsonJsonParser
 * @see YamlJsonParser
 * @see BasicJsonParser
 */
public abstract class JsonParserFactory {

	/**
	 * Static factory for the "best" JSON parser available on the classpath. Tries
	 * Jackson, then Gson, Snake YAML, and then falls back to the {@link BasicJsonParser}.
	 * @return a {@link JsonParser}
	 */
	public static JsonParser getJsonParser() {
		if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
			return new JacksonJsonParser();
		}
		if (ClassUtils.isPresent("com.google.gson.Gson", null)) {
			return new GsonJsonParser();
		}
		if (ClassUtils.isPresent("org.yaml.snakeyaml.Yaml", null)) {
			return new YamlJsonParser();
		}
		return new BasicJsonParser();
	}

}
