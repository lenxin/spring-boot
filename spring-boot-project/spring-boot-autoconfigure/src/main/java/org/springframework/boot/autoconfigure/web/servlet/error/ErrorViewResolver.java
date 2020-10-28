package org.springframework.boot.autoconfigure.web.servlet.error;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interface that can be implemented by beans that resolve error views.
 *

 * @since 1.4.0
 */
@FunctionalInterface
public interface ErrorViewResolver {

	/**
	 * Resolve an error view for the specified details.
	 * @param request the source request
	 * @param status the http status of the error
	 * @param model the suggested model to be used with the view
	 * @return a resolved {@link ModelAndView} or {@code null}
	 */
	ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model);

}
