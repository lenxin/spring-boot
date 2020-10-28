package org.springframework.boot.docs.web.client;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Example configuration for using a {@link RestTemplateCustomizer} to configure a proxy.
 *

 */
public class RestTemplateProxyCustomizationExample {

	/**
	 * A {@link RestTemplateCustomizer} that applies an HttpComponents-based request
	 * factory that is configured to use a proxy.
	 */
	// tag::customizer[]
	static class ProxyCustomizer implements RestTemplateCustomizer {

		@Override
		public void customize(RestTemplate restTemplate) {
			HttpHost proxy = new HttpHost("proxy.example.com");
			HttpClient httpClient = HttpClientBuilder.create().setRoutePlanner(new DefaultProxyRoutePlanner(proxy) {

				@Override
				public HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context)
						throws HttpException {
					if (target.getHostName().equals("192.168.0.5")) {
						return null;
					}
					return super.determineProxy(target, request, context);
				}

			}).build();
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
		}

	}
	// end::customizer[]

}
