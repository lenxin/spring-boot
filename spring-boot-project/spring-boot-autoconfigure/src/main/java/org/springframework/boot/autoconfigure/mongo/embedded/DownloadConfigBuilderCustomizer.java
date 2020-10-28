package org.springframework.boot.autoconfigure.mongo.embedded;

import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.process.config.store.IDownloadConfig;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link IDownloadConfig} via a {@link DownloadConfigBuilder} whilst retaining default
 * auto-configuration.
 *

 * @since 2.2.0
 */
@FunctionalInterface
public interface DownloadConfigBuilderCustomizer {

	/**
	 * Customize the {@link DownloadConfigBuilder}.
	 * @param downloadConfigBuilder the {@link DownloadConfigBuilder} to customize
	 */
	void customize(DownloadConfigBuilder downloadConfigBuilder);

}
