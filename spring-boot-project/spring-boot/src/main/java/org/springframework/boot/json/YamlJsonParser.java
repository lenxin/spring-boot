package org.springframework.boot.json;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import org.springframework.util.Assert;

/**
 * Thin wrapper to adapt Snake {@link Yaml} to {@link JsonParser}.
 *


 * @since 1.0.0
 * @see JsonParserFactory
 */
public class YamlJsonParser extends AbstractJsonParser {

	private final Yaml yaml = new Yaml(new TypeLimitedConstructor());

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> parseMap(String json) {
		return parseMap(json, (trimmed) -> this.yaml.loadAs(trimmed, Map.class));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> parseList(String json) {
		return parseList(json, (trimmed) -> this.yaml.loadAs(trimmed, List.class));
	}

	private static class TypeLimitedConstructor extends Constructor {

		private static final Set<String> SUPPORTED_TYPES;
		static {
			Set<Class<?>> supportedTypes = new LinkedHashSet<>();
			supportedTypes.add(List.class);
			supportedTypes.add(Map.class);
			SUPPORTED_TYPES = supportedTypes.stream().map(Class::getName)
					.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
		}

		@Override
		protected Class<?> getClassForName(String name) throws ClassNotFoundException {
			Assert.state(SUPPORTED_TYPES.contains(name),
					() -> "Unsupported '" + name + "' type encountered in YAML document");
			return super.getClassForName(name);
		}

	}

}
