package org.springframework.boot.autoconfigure.jms.artemis;

import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;

/**
 * Callback interface that can be implemented by beans wishing to customize the Artemis
 * JMS server {@link Configuration} before it is used by an auto-configured
 * {@link EmbeddedActiveMQ} instance.
 *


 * @since 1.3.0
 * @see ArtemisAutoConfiguration
 */
@FunctionalInterface
public interface ArtemisConfigurationCustomizer {

	/**
	 * Customize the configuration.
	 * @param configuration the configuration to customize
	 */
	void customize(Configuration configuration);

}
