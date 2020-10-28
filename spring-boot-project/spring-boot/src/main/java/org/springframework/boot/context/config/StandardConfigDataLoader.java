package org.springframework.boot.context.config;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedResource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

/**
 * {@link ConfigDataLoader} for {@link Resource} backed locations.
 *


 * @since 2.4.0
 */
public class StandardConfigDataLoader implements ConfigDataLoader<StandardConfigDataResource> {

	@Override
	public ConfigData load(ConfigDataLoaderContext context, StandardConfigDataResource resource)
			throws IOException, ConfigDataNotFoundException {
		ConfigDataResourceNotFoundException.throwIfDoesNotExist(resource, resource.getResource());
		StandardConfigDataReference reference = resource.getReference();
		Resource originTrackedResource = OriginTrackedResource.of(resource.getResource(),
				Origin.from(reference.getConfigDataLocation()));
		String name = String.format("Config resource '%s' via location '%s'", reference.getResourceLocation(),
				reference.getConfigDataLocation());
		List<PropertySource<?>> propertySources = reference.getPropertySourceLoader().load(name, originTrackedResource);
		return new ConfigData(propertySources);
	}

}
