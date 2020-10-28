package org.springframework.boot.context.config;

import java.util.Collections;
import java.util.List;

/**
 * {@link ConfigDataLocationResolver} for config tree locations.
 *


 * @since 2.4.0
 */
public class ConfigTreeConfigDataLocationResolver implements ConfigDataLocationResolver<ConfigTreeConfigDataResource> {

	private static final String PREFIX = "configtree:";

	@Override
	public boolean isResolvable(ConfigDataLocationResolverContext context, ConfigDataLocation location) {
		return location.hasPrefix(PREFIX);
	}

	@Override
	public List<ConfigTreeConfigDataResource> resolve(ConfigDataLocationResolverContext context,
			ConfigDataLocation location) {
		ConfigTreeConfigDataResource resolved = new ConfigTreeConfigDataResource(location.getNonPrefixedValue(PREFIX));
		return Collections.singletonList(resolved);
	}

}
