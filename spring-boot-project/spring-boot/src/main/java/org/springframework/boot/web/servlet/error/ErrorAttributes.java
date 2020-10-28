package org.springframework.boot.web.servlet.error;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * Provides access to error attributes which can be logged or presented to the user.
 *


 * @since 2.0.0
 * @see DefaultErrorAttributes
 */
public interface ErrorAttributes {

	/**
	 * Returns a {@link Map} of the error attributes. The map can be used as the model of
	 * an error page {@link ModelAndView}, or returned as a
	 * {@link ResponseBody @ResponseBody}.
	 * @param webRequest the source request
	 * @param includeStackTrace if stack trace element should be included
	 * @return a map of error attributes
	 * @deprecated since 2.3.0 in favor of
	 * {@link #getErrorAttributes(WebRequest, ErrorAttributeOptions)}
	 */
	@Deprecated
	default Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		return Collections.emptyMap();
	}

	/**
	 * Returns a {@link Map} of the error attributes. The map can be used as the model of
	 * an error page {@link ModelAndView}, or returned as a
	 * {@link ResponseBody @ResponseBody}.
	 * @param webRequest the source request
	 * @param options options for error attribute contents
	 * @return a map of error attributes
	 * @since 2.3.0
	 */
	default Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		return getErrorAttributes(webRequest, options.isIncluded(Include.STACK_TRACE));
	}

	/**
	 * Return the underlying cause of the error or {@code null} if the error cannot be
	 * extracted.
	 * @param webRequest the source request
	 * @return the {@link Exception} that caused the error or {@code null}
	 */
	Throwable getError(WebRequest webRequest);

}
