package org.springframework.boot.context.config;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

/**
 * {@link PropertySourceLoader} for tests.
 *

 */
class TestPropertySourceLoader1 implements PropertySourceLoader {

	@Override
	public String[] getFileExtensions() {
		return new String[] { "custom" };
	}

	@Override
	public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
		Map<String, Object> map = Collections.singletonMap("customloader1", "true");
		return Collections.singletonList(new MapPropertySource(name, map));
	}

}
