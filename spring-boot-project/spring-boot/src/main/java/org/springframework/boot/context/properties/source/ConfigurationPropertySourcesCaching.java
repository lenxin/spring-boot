package org.springframework.boot.context.properties.source;

import java.time.Duration;
import java.util.function.Consumer;

/**
 * {@link ConfigurationPropertyCaching} for an {@link Iterable iterable} set of
 * {@link ConfigurationPropertySource} instances.
 *

 */
class ConfigurationPropertySourcesCaching implements ConfigurationPropertyCaching {

	private final Iterable<ConfigurationPropertySource> sources;

	ConfigurationPropertySourcesCaching(Iterable<ConfigurationPropertySource> sources) {
		this.sources = sources;
	}

	@Override
	public void enable() {
		forEach(ConfigurationPropertyCaching::enable);
	}

	@Override
	public void disable() {
		forEach(ConfigurationPropertyCaching::disable);
	}

	@Override
	public void setTimeToLive(Duration timeToLive) {
		forEach((caching) -> caching.setTimeToLive(timeToLive));
	}

	@Override
	public void clear() {
		forEach(ConfigurationPropertyCaching::clear);
	}

	private void forEach(Consumer<ConfigurationPropertyCaching> action) {
		if (this.sources != null) {
			for (ConfigurationPropertySource source : this.sources) {
				ConfigurationPropertyCaching caching = CachingConfigurationPropertySource.find(source);
				if (caching != null) {
					action.accept(caching);
				}
			}
		}
	}

}
