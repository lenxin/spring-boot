package org.springframework.boot.docs.context.embedded;

import org.apache.catalina.Context;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.junit.jupiter.api.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.docs.context.embedded.TomcatLegacyCookieProcessorExample.LegacyCookieProcessorConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TomcatLegacyCookieProcessorExample}.
 *

 */
class TomcatLegacyCookieProcessorExampleTests {

	@Test
	void cookieProcessorIsCustomized() {
		ServletWebServerApplicationContext applicationContext = (ServletWebServerApplicationContext) new SpringApplication(
				TestConfiguration.class, LegacyCookieProcessorConfiguration.class).run();
		Context context = (Context) ((TomcatWebServer) applicationContext.getWebServer()).getTomcat().getHost()
				.findChildren()[0];
		assertThat(context.getCookieProcessor()).isInstanceOf(LegacyCookieProcessor.class);
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		@Bean
		TomcatServletWebServerFactory tomcatFactory() {
			return new TomcatServletWebServerFactory(0);
		}

		@Bean
		WebServerFactoryCustomizerBeanPostProcessor postProcessor() {
			return new WebServerFactoryCustomizerBeanPostProcessor();
		}

	}

}
