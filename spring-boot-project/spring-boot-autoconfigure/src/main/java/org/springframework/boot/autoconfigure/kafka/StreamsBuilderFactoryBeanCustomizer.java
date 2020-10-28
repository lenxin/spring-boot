package org.springframework.boot.autoconfigure.kafka;

import org.springframework.kafka.config.StreamsBuilderFactoryBean;

/**
 * Callback interface for customizing {@code StreamsBuilderFactoryBean} beans.
 *

 * @since 2.3.2
 */
@FunctionalInterface
public interface StreamsBuilderFactoryBeanCustomizer {

	/**
	 * Customize the {@link StreamsBuilderFactoryBean}.
	 * @param factoryBean the factory bean to customize
	 */
	void customize(StreamsBuilderFactoryBean factoryBean);

}
