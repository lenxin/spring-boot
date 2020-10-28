package org.springframework.boot.web.reactive.error;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;

/**
 * Provides access to error attributes which can be logged or presented to the user.
 *


 * @since 2.0.0
 * @see DefaultErrorAttributes
 */
public interface ErrorAttributes {

	/**
	 * Return a {@link Map} of the error attributes. The map can be used as the model of
	 * an error page, or returned as a {@link ServerResponse} body.
	 * @param request the source request
	 * @param includeStackTrace if stack trace attribute should be included
	 * @return a map of error attributes
	 * @deprecated since 2.3.0 in favor of
	 * {@link #getErrorAttributes(ServerRequest, ErrorAttributeOptions)}
	 */
	@Deprecated
	default Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
		return Collections.emptyMap();
	}

	/**
	 * Return a {@link Map} of the error attributes. The map can be used as the model of
	 * an error page, or returned as a {@link ServerResponse} body.
	 * @param request the source request
	 * @param options options for error attribute contents
	 * @return a map of error attributes
	 */
	default Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		return getErrorAttributes(request, options.isIncluded(Include.STACK_TRACE));
	}

	/**
	 * Return the underlying cause of the error or {@code null} if the error cannot be
	 * extracted.
	 * @param request the source ServerRequest
	 * @return the {@link Exception} that caused the error or {@code null}
	 */
	Throwable getError(ServerRequest request);

	/**
	 * Store the given error information in the current {@link ServerWebExchange}.
	 * @param error the {@link Exception} that caused the error
	 * @param exchange the source exchange
	 */
	void storeErrorInformation(Throwable error, ServerWebExchange exchange);

}
