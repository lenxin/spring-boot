package org.springframework.boot.web.reactive.error;

import org.springframework.web.server.WebExceptionHandler;

/**
 * Marker interface that indicates that a {@link WebExceptionHandler} is used to render
 * errors.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface ErrorWebExceptionHandler extends WebExceptionHandler {

}
