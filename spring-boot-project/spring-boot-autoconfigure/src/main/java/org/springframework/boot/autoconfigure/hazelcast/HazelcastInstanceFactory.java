package org.springframework.boot.autoconfigure.hazelcast;

import java.io.IOException;
import java.net.URL;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.config.YamlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

/**
 * Factory that can be used to create a {@link HazelcastInstance}.
 *


 * @since 1.3.0
 */
public class HazelcastInstanceFactory {

	private final Config config;

	/**
	 * Create a {@link HazelcastInstanceFactory} for the specified configuration location.
	 * @param configLocation the location of the configuration file
	 * @throws IOException if the configuration location could not be read
	 */
	public HazelcastInstanceFactory(Resource configLocation) throws IOException {
		Assert.notNull(configLocation, "ConfigLocation must not be null");
		this.config = getConfig(configLocation);
	}

	/**
	 * Create a {@link HazelcastInstanceFactory} for the specified configuration.
	 * @param config the configuration
	 */
	public HazelcastInstanceFactory(Config config) {
		Assert.notNull(config, "Config must not be null");
		this.config = config;
	}

	private Config getConfig(Resource configLocation) throws IOException {
		URL configUrl = configLocation.getURL();
		Config config = createConfig(configUrl);
		if (ResourceUtils.isFileURL(configUrl)) {
			config.setConfigurationFile(configLocation.getFile());
		}
		else {
			config.setConfigurationUrl(configUrl);
		}
		return config;
	}

	private static Config createConfig(URL configUrl) throws IOException {
		String configFileName = configUrl.getPath();
		if (configFileName.endsWith(".yaml")) {
			return new YamlConfigBuilder(configUrl).build();
		}
		return new XmlConfigBuilder(configUrl).build();
	}

	/**
	 * Get the {@link HazelcastInstance}.
	 * @return the {@link HazelcastInstance}
	 */
	public HazelcastInstance getHazelcastInstance() {
		if (StringUtils.hasText(this.config.getInstanceName())) {
			return Hazelcast.getOrCreateHazelcastInstance(this.config);
		}
		return Hazelcast.newHazelcastInstance(this.config);
	}

}
