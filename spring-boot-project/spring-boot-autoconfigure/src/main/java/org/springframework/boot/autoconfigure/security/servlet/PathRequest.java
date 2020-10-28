package org.springframework.boot.autoconfigure.security.servlet;

import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.security.servlet.ApplicationContextRequestMatcher;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.context.WebApplicationContext;

/**
 * Factory that can be used to create a {@link RequestMatcher} for commonly used paths.
 *


 * @since 2.0.0
 */
public final class PathRequest {

	private PathRequest() {
	}

	/**
	 * Returns a {@link StaticResourceRequest} that can be used to create a matcher for
	 * {@link StaticResourceLocation locations}.
	 * @return a {@link StaticResourceRequest}
	 */
	public static StaticResourceRequest toStaticResources() {
		return StaticResourceRequest.INSTANCE;
	}

	/**
	 * Returns a matcher that includes the H2 console location. For example:
	 * <pre class="code">
	 * PathRequest.toH2Console()
	 * </pre>
	 * @return the configured {@link RequestMatcher}
	 */
	public static H2ConsoleRequestMatcher toH2Console() {
		return new H2ConsoleRequestMatcher();
	}

	/**
	 * The request matcher used to match against h2 console path.
	 */
	public static final class H2ConsoleRequestMatcher extends ApplicationContextRequestMatcher<H2ConsoleProperties> {

		private volatile RequestMatcher delegate;

		private H2ConsoleRequestMatcher() {
			super(H2ConsoleProperties.class);
		}

		@Override
		protected boolean ignoreApplicationContext(WebApplicationContext applicationContext) {
			return WebServerApplicationContext.hasServerNamespace(applicationContext, "management");
		}

		@Override
		protected void initialized(Supplier<H2ConsoleProperties> h2ConsoleProperties) {
			this.delegate = new AntPathRequestMatcher(h2ConsoleProperties.get().getPath() + "/**");
		}

		@Override
		protected boolean matches(HttpServletRequest request, Supplier<H2ConsoleProperties> context) {
			return this.delegate.matches(request);
		}

	}

}
