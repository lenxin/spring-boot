package org.springframework.boot.web.client;

import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInitializer;
import org.springframework.web.client.RestTemplate;

/**
 * Callback interface that can be used to customize the {@link ClientHttpRequest} sent
 * from a {@link RestTemplate}.
 *
 * @param <T> the {@link ClientHttpRequest} type


 * @since 2.2.0
 * @see RestTemplateBuilder
 * @see ClientHttpRequestInitializer
 */
@FunctionalInterface
public interface RestTemplateRequestCustomizer<T extends ClientHttpRequest> {

	/**
	 * Customize the specified {@link ClientHttpRequest}.
	 * @param request the request to customize
	 */
	void customize(T request);

}
