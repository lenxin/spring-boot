package org.springframework.boot.autoconfigure.web.reactive.function.client;

import reactor.netty.http.client.HttpClient;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;

/**
 * Mapper that allows for custom modification of a {@link HttpClient} before it is used as
 * the basis for a {@link ReactorClientHttpConnector}.
 *

 * @since 2.3.0
 */
@FunctionalInterface
public interface ReactorNettyHttpClientMapper {

	/**
	 * Configure the given {@link HttpClient} and return the newly created instance.
	 * @param httpClient the client to configure
	 * @return the new client instance
	 */
	HttpClient configure(HttpClient httpClient);

}
