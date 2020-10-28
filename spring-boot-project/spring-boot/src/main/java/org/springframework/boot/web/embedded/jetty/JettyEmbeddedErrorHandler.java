package org.springframework.boot.web.embedded.jetty;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;

/**
 * Variation of Jetty's {@link ErrorPageErrorHandler} that supports all {@link HttpMethod
 * HttpMethods} rather than just {@code GET}, {@code POST} and {@code HEAD}. By default
 * Jetty <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=446039">intentionally only
 * supports a limited set of HTTP methods</a> for error pages, however, Spring Boot
 * prefers Tomcat, Jetty and Undertow to all behave in the same way.
 *


 */
class JettyEmbeddedErrorHandler extends ErrorPageErrorHandler {

	private static final Set<String> HANDLED_HTTP_METHODS = new HashSet<>(Arrays.asList("GET", "POST", "HEAD"));

	@Override
	public boolean errorPageForMethod(String method) {
		return true;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (!HANDLED_HTTP_METHODS.contains(baseRequest.getMethod())) {
			baseRequest.setMethod("GET");
		}
		super.doError(target, baseRequest, request, response);
	}

}
