package org.springframework.boot.maven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for working with Env variables.
 *

 */
class EnvVariables {

	private final Map<String, String> variables;

	EnvVariables(Map<String, String> variables) {
		this.variables = parseEnvVariables(variables);
	}

	private static Map<String, String> parseEnvVariables(Map<String, String> args) {
		if (args == null || args.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, String> result = new LinkedHashMap<>();
		for (Map.Entry<String, String> e : args.entrySet()) {
			if (e.getKey() != null) {
				result.put(e.getKey(), getValue(e.getValue()));
			}
		}
		return result;
	}

	private static String getValue(String value) {
		return (value != null) ? value : "";
	}

	Map<String, String> asMap() {
		return Collections.unmodifiableMap(this.variables);
	}

	String[] asArray() {
		List<String> args = new ArrayList<>(this.variables.size());
		for (Map.Entry<String, String> arg : this.variables.entrySet()) {
			args.add(arg.getKey() + "=" + arg.getValue());
		}
		return args.toArray(new String[0]);
	}

}
