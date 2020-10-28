package org.springframework.boot.autoconfigure.web.servlet;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;
import org.springframework.util.ObjectUtils;

/**
 * {@link WebServerFactoryCustomizer} to apply {@link ServerProperties} to Tomcat web
 * servers.
 *


 * @since 2.0.0
 */
public class TomcatServletWebServerFactoryCustomizer
		implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>, Ordered {

	private final ServerProperties serverProperties;

	public TomcatServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		ServerProperties.Tomcat tomcatProperties = this.serverProperties.getTomcat();
		if (!ObjectUtils.isEmpty(tomcatProperties.getAdditionalTldSkipPatterns())) {
			factory.getTldSkipPatterns().addAll(tomcatProperties.getAdditionalTldSkipPatterns());
		}
		if (tomcatProperties.getRedirectContextRoot() != null) {
			customizeRedirectContextRoot(factory, tomcatProperties.getRedirectContextRoot());
		}
		customizeUseRelativeRedirects(factory, tomcatProperties.isUseRelativeRedirects());
		factory.setDisableMBeanRegistry(!tomcatProperties.getMbeanregistry().isEnabled());
	}

	private void customizeRedirectContextRoot(ConfigurableTomcatWebServerFactory factory, boolean redirectContextRoot) {
		factory.addContextCustomizers((context) -> context.setMapperContextRootRedirectEnabled(redirectContextRoot));
	}

	private void customizeUseRelativeRedirects(ConfigurableTomcatWebServerFactory factory,
			boolean useRelativeRedirects) {
		factory.addContextCustomizers((context) -> context.setUseRelativeRedirects(useRelativeRedirects));
	}

}
