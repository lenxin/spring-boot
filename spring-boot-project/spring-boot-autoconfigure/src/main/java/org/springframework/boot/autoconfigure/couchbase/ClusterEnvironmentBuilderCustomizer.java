package org.springframework.boot.autoconfigure.couchbase;

import com.couchbase.client.java.env.ClusterEnvironment;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link ClusterEnvironment} via a
 * {@link com.couchbase.client.java.env.ClusterEnvironment.Builder
 * ClusterEnvironment.Builder} whilst retaining default auto-configuration.whilst
 * retaining default auto-configuration.
 *

 * @since 2.3.0
 */
@FunctionalInterface
public interface ClusterEnvironmentBuilderCustomizer {

	/**
	 * Customize the {@link com.couchbase.client.java.env.ClusterEnvironment.Builder
	 * ClusterEnvironment.Builder}.
	 * @param builder the builder to customize
	 */
	void customize(ClusterEnvironment.Builder builder);

}
