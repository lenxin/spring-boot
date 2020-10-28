package org.springframework.boot.autoconfigure.session;

import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link DefaultCookieSerializer} configuration.
 *

 * @since 2.3.0
 */
@FunctionalInterface
public interface DefaultCookieSerializerCustomizer {

	/**
	 * Customize the cookie serializer.
	 * @param cookieSerializer the {@code DefaultCookieSerializer} to customize
	 */
	void customize(DefaultCookieSerializer cookieSerializer);

}
