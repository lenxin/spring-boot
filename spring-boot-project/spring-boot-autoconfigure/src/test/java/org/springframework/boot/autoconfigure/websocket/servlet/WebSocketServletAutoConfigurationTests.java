package org.springframework.boot.autoconfigure.websocket.servlet;

import javax.websocket.server.ServerContainer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebSocketServletAutoConfiguration}
 *

 */
class WebSocketServletAutoConfigurationTests {

	private AnnotationConfigServletWebServerApplicationContext context;

	@BeforeEach
	void createContext() {
		this.context = new AnnotationConfigServletWebServerApplicationContext();
	}

	@AfterEach
	void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	void tomcatServerContainerIsAvailableFromTheServletContext() {
		serverContainerIsAvailableFromTheServletContext(TomcatConfiguration.class,
				WebSocketServletAutoConfiguration.TomcatWebSocketConfiguration.class);
	}

	@Test
	void jettyServerContainerIsAvailableFromTheServletContext() {
		serverContainerIsAvailableFromTheServletContext(JettyConfiguration.class,
				WebSocketServletAutoConfiguration.JettyWebSocketConfiguration.class);
	}

	private void serverContainerIsAvailableFromTheServletContext(Class<?>... configuration) {
		this.context.register(configuration);
		this.context.refresh();
		Object serverContainer = this.context.getServletContext()
				.getAttribute("javax.websocket.server.ServerContainer");
		assertThat(serverContainer).isInstanceOf(ServerContainer.class);

	}

	@Configuration(proxyBeanMethods = false)
	static class CommonConfiguration {

		@Bean
		WebServerFactoryCustomizerBeanPostProcessor ServletWebServerCustomizerBeanPostProcessor() {
			return new WebServerFactoryCustomizerBeanPostProcessor();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class TomcatConfiguration extends CommonConfiguration {

		@Bean
		ServletWebServerFactory webServerFactory() {
			TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
			factory.setPort(0);
			return factory;
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class JettyConfiguration extends CommonConfiguration {

		@Bean
		ServletWebServerFactory webServerFactory() {
			JettyServletWebServerFactory JettyServletWebServerFactory = new JettyServletWebServerFactory();
			JettyServletWebServerFactory.setPort(0);
			return JettyServletWebServerFactory;
		}

	}

}
