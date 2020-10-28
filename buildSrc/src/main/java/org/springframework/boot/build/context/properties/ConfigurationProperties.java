package org.springframework.boot.build.context.properties;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.gradle.api.file.FileCollection;

/**
 * Configuration properties read from one or more
 * {@code META-INF/spring-configuration-metadata.json} files.
 *

 */
final class ConfigurationProperties {

	private static final Type MAP_TYPE = new MapTypeToken().getType();

	private ConfigurationProperties() {

	}

	@SuppressWarnings("unchecked")
	static Map<String, ConfigurationProperty> fromFiles(FileCollection files) {
		List<ConfigurationProperty> configurationProperties = new ArrayList<>();
		try {
			Gson gson = new GsonBuilder().create();
			for (File file : files) {
				try (Reader reader = new FileReader(file)) {
					Map<String, Object> json = gson.fromJson(reader, MAP_TYPE);
					List<Map<String, Object>> properties = (List<Map<String, Object>>) json.get("properties");
					for (Map<String, Object> property : properties) {
						String name = (String) property.get("name");
						String type = (String) property.get("type");
						Object defaultValue = property.get("defaultValue");
						String description = (String) property.get("description");
						boolean deprecated = property.containsKey("deprecated");
						configurationProperties
								.add(new ConfigurationProperty(name, type, defaultValue, description, deprecated));
					}
				}
			}
			return configurationProperties.stream()
					.collect(Collectors.toMap(ConfigurationProperty::getName, Function.identity()));
		}
		catch (IOException ex) {
			throw new RuntimeException("Failed to load configuration metadata", ex);
		}
	}

	private static final class MapTypeToken extends TypeToken<Map<String, Object>> {

	}

}
