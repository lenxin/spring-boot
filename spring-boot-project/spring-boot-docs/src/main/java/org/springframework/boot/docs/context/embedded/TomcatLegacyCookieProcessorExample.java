package org.springframework.boot.docs.context.embedded;

import org.apache.tomcat.util.http.LegacyCookieProcessor;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Example configuration for configuring Tomcat with to use {@link LegacyCookieProcessor}.
 *

 */
public class TomcatLegacyCookieProcessorExample {

	/**
	 * Configuration class that declares the required {@link WebServerFactoryCustomizer}.
	 */
	@Configuration(proxyBeanMethods = false)
	public static class LegacyCookieProcessorConfiguration {

		// tag::customizer[]
		@Bean
		public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
			return (factory) -> factory
					.addContextCustomizers((context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
		}
		// end::customizer[]

	}

}
