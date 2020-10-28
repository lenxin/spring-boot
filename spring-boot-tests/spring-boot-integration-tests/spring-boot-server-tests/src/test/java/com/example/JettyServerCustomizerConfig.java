package com.example;

import org.eclipse.jetty.server.handler.ContextHandler;

import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link JettyServerCustomizer} that approves all aliases (Used for Windows CI on
 * Concourse).
 *

 */
@Configuration(proxyBeanMethods = false)
public class JettyServerCustomizerConfig {

	@Bean
	public JettyServerCustomizer jettyServerCustomizer() {
		return (server) -> {
			ContextHandler handler = (ContextHandler) server.getHandler();
			handler.addAliasCheck(new ContextHandler.ApproveAliases());
		};
	}

}
