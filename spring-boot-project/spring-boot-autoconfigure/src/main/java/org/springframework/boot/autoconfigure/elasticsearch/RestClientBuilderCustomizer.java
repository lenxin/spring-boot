package org.springframework.boot.autoconfigure.elasticsearch;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClientBuilder;

/**
 * Callback interface that can be implemented by beans wishing to further customize the
 * {@link org.elasticsearch.client.RestClient} via a {@link RestClientBuilder} whilst
 * retaining default auto-configuration.
 *


 * @since 2.1.0
 */
@FunctionalInterface
public interface RestClientBuilderCustomizer {

	/**
	 * Customize the {@link RestClientBuilder}.
	 * <p>
	 * Possibly overrides customizations made with the {@code "spring.elasticsearch.rest"}
	 * configuration properties namespace. For more targeted changes, see
	 * {@link #customize(HttpAsyncClientBuilder)} and
	 * {@link #customize(RequestConfig.Builder)}.
	 * @param builder the builder to customize
	 */
	void customize(RestClientBuilder builder);

	/**
	 * Customize the {@link HttpAsyncClientBuilder}.
	 * @param builder the builder
	 * @since 2.3.0
	 */
	default void customize(HttpAsyncClientBuilder builder) {
	}

	/**
	 * Customize the {@link RequestConfig.Builder}.
	 * @param builder the builder
	 * @since 2.3.0
	 */
	default void customize(RequestConfig.Builder builder) {
	}

}
