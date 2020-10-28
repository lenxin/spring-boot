package org.springframework.boot.json;

import java.util.List;
import java.util.Map;

/**
 * Parser that can read JSON formatted strings into {@link Map}s or {@link List}s.
 *

 * @since 1.0.0
 * @see JsonParserFactory
 * @see BasicJsonParser
 * @see JacksonJsonParser
 * @see GsonJsonParser
 * @see YamlJsonParser
 */
public interface JsonParser {

	/**
	 * Parse the specified JSON string into a Map.
	 * @param json the JSON to parse
	 * @return the parsed JSON as a map
	 * @throws JsonParseException if the JSON cannot be parsed
	 */
	Map<String, Object> parseMap(String json) throws JsonParseException;

	/**
	 * Parse the specified JSON string into a List.
	 * @param json the JSON to parse
	 * @return the parsed JSON as a list
	 * @throws JsonParseException if the JSON cannot be parsed
	 */
	List<Object> parseList(String json) throws JsonParseException;

}
