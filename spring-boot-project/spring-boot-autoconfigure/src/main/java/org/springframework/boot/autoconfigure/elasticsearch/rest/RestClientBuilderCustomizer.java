package org.springframework.boot.autoconfigure.elasticsearch.rest;

import org.elasticsearch.client.RestClientBuilder;

/**
 * Callback interface that can be implemented by beans wishing to further customize the
 * {@link org.elasticsearch.client.RestClient} via a {@link RestClientBuilder} whilst
 * retaining default auto-configuration.
 *

 * @since 2.1.0
 * @deprecated as of 2.3.1 in favor of
 * {@link org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer}
 */
@FunctionalInterface
@Deprecated
public interface RestClientBuilderCustomizer
		extends org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer {

}
