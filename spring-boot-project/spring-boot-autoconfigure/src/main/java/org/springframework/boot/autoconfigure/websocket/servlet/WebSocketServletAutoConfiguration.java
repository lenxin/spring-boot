package org.springframework.boot.autoconfigure.websocket.servlet;

import javax.servlet.Servlet;
import javax.websocket.server.ServerContainer;

import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.websocket.server.WsSci;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto configuration for WebSocket servlet server in embedded Tomcat, Jetty or Undertow.
 * Requires the appropriate WebSocket modules to be on the classpath.
 * <p>
 * If Tomcat's WebSocket support is detected on the classpath we add a customizer that
 * installs the Tomcat WebSocket initializer. In a non-embedded server it should already
 * be there.
 * <p>
 * If Jetty's WebSocket support is detected on the classpath we add a configuration that
 * configures the context with WebSocket support. In a non-embedded server it should
 * already be there.
 * <p>
 * If Undertow's WebSocket support is detected on the classpath we add a customizer that
 * installs the Undertow WebSocket DeploymentInfo Customizer. In a non-embedded server it
 * should already be there.
 *



 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Servlet.class, ServerContainer.class })
@ConditionalOnWebApplication(type = Type.SERVLET)
@AutoConfigureBefore(ServletWebServerFactoryAutoConfiguration.class)
public class WebSocketServletAutoConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ Tomcat.class, WsSci.class })
	static class TomcatWebSocketConfiguration {

		@Bean
		@ConditionalOnMissingBean(name = "websocketServletWebServerCustomizer")
		TomcatWebSocketServletWebServerCustomizer websocketServletWebServerCustomizer() {
			return new TomcatWebSocketServletWebServerCustomizer();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(WebSocketServerContainerInitializer.class)
	static class JettyWebSocketConfiguration {

		@Bean
		@ConditionalOnMissingBean(name = "websocketServletWebServerCustomizer")
		JettyWebSocketServletWebServerCustomizer websocketServletWebServerCustomizer() {
			return new JettyWebSocketServletWebServerCustomizer();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(io.undertow.websockets.jsr.Bootstrap.class)
	static class UndertowWebSocketConfiguration {

		@Bean
		@ConditionalOnMissingBean(name = "websocketServletWebServerCustomizer")
		UndertowWebSocketServletWebServerCustomizer websocketServletWebServerCustomizer() {
			return new UndertowWebSocketServletWebServerCustomizer();
		}

	}

}
