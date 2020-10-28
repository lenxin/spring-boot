package org.springframework.boot.autoconfigure.hazelcast;

import java.io.IOException;
import java.net.URL;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.client.config.YamlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;

import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Factory that can be used to create a client {@link HazelcastInstance}.
 *

 * @since 2.0.0
 */
public class HazelcastClientFactory {

	private final ClientConfig clientConfig;

	/**
	 * Create a {@link HazelcastClientFactory} for the specified configuration location.
	 * @param clientConfigLocation the location of the configuration file
	 * @throws IOException if the configuration location could not be read
	 */
	public HazelcastClientFactory(Resource clientConfigLocation) throws IOException {
		this.clientConfig = getClientConfig(clientConfigLocation);
	}

	/**
	 * Create a {@link HazelcastClientFactory} for the specified configuration.
	 * @param clientConfig the configuration
	 */
	public HazelcastClientFactory(ClientConfig clientConfig) {
		Assert.notNull(clientConfig, "ClientConfig must not be null");
		this.clientConfig = clientConfig;
	}

	private ClientConfig getClientConfig(Resource clientConfigLocation) throws IOException {
		URL configUrl = clientConfigLocation.getURL();
		String configFileName = configUrl.getPath();
		if (configFileName.endsWith(".yaml")) {
			return new YamlClientConfigBuilder(configUrl).build();
		}
		return new XmlClientConfigBuilder(configUrl).build();
	}

	/**
	 * Get the {@link HazelcastInstance}.
	 * @return the {@link HazelcastInstance}
	 */
	public HazelcastInstance getHazelcastInstance() {
		if (StringUtils.hasText(this.clientConfig.getInstanceName())) {
			return HazelcastClient.getOrCreateHazelcastClient(this.clientConfig);
		}
		return HazelcastClient.newHazelcastClient(this.clientConfig);
	}

}
