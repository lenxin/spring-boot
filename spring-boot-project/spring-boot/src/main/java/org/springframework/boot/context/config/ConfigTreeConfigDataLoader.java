package org.springframework.boot.context.config;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;

import org.springframework.boot.env.ConfigTreePropertySource;

/**
 * {@link ConfigDataLoader} for config tree locations.
 *


 * @since 2.4.0
 */
public class ConfigTreeConfigDataLoader implements ConfigDataLoader<ConfigTreeConfigDataResource> {

	@Override
	public ConfigData load(ConfigDataLoaderContext context, ConfigTreeConfigDataResource resource)
			throws IOException, ConfigDataResourceNotFoundException {
		Path path = resource.getPath();
		ConfigDataResourceNotFoundException.throwIfDoesNotExist(resource, path);
		String name = "Config tree '" + path + "'";
		ConfigTreePropertySource source = new ConfigTreePropertySource(name, path);
		return new ConfigData(Collections.singletonList(source));
	}

}
