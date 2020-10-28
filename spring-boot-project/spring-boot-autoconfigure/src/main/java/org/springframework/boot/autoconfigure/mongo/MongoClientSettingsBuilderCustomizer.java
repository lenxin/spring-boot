package org.springframework.boot.autoconfigure.mongo;

import com.mongodb.MongoClientSettings.Builder;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link com.mongodb.MongoClientSettings} via a {@link Builder
 * MongoClientSettings.Builder} whilst retaining default auto-configuration.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface MongoClientSettingsBuilderCustomizer {

	/**
	 * Customize the {@link Builder}.
	 * @param clientSettingsBuilder the builder to customize
	 */
	void customize(Builder clientSettingsBuilder);

}
