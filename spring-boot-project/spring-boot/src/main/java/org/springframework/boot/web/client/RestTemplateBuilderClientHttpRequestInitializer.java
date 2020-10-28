package org.springframework.boot.web.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.util.LambdaSafe;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInitializer;

/**
 * {@link ClientHttpRequestFactory} to apply customizations from the
 * {@link RestTemplateBuilder}.
 *


 */
class RestTemplateBuilderClientHttpRequestInitializer implements ClientHttpRequestInitializer {

	private final BasicAuthentication basicAuthentication;

	private final Map<String, List<String>> defaultHeaders;

	private final Set<RestTemplateRequestCustomizer<?>> requestCustomizers;

	RestTemplateBuilderClientHttpRequestInitializer(BasicAuthentication basicAuthentication,
			Map<String, List<String>> defaultHeaders, Set<RestTemplateRequestCustomizer<?>> requestCustomizers) {
		this.basicAuthentication = basicAuthentication;
		this.defaultHeaders = defaultHeaders;
		this.requestCustomizers = requestCustomizers;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initialize(ClientHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		if (this.basicAuthentication != null) {
			this.basicAuthentication.applyTo(headers);
		}
		this.defaultHeaders.forEach(headers::putIfAbsent);
		LambdaSafe.callbacks(RestTemplateRequestCustomizer.class, this.requestCustomizers, request)
				.invoke((customizer) -> customizer.customize(request));
	}

}
