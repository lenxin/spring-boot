package org.springframework.boot.autoconfigure.web.servlet;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.WebListenerRegistrar;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.core.Ordered;

/**
 * {@link WebServerFactoryCustomizer} to apply {@link ServerProperties} to servlet web
 * servers.
 *




 * @since 2.0.0
 */
public class ServletWebServerFactoryCustomizer
		implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>, Ordered {

	private final ServerProperties serverProperties;

	private final Iterable<WebListenerRegistrar> webListenerRegistrars;

	public ServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
		this(serverProperties, Collections.emptyList());
	}

	public ServletWebServerFactoryCustomizer(ServerProperties serverProperties,
			List<WebListenerRegistrar> webListenerRegistrars) {
		this.serverProperties = serverProperties;
		this.webListenerRegistrars = webListenerRegistrars;
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		map.from(this.serverProperties::getPort).to(factory::setPort);
		map.from(this.serverProperties::getAddress).to(factory::setAddress);
		map.from(this.serverProperties.getServlet()::getContextPath).to(factory::setContextPath);
		map.from(this.serverProperties.getServlet()::getApplicationDisplayName).to(factory::setDisplayName);
		map.from(this.serverProperties.getServlet()::isRegisterDefaultServlet).to(factory::setRegisterDefaultServlet);
		map.from(this.serverProperties.getServlet()::getSession).to(factory::setSession);
		map.from(this.serverProperties::getSsl).to(factory::setSsl);
		map.from(this.serverProperties.getServlet()::getJsp).to(factory::setJsp);
		map.from(this.serverProperties::getCompression).to(factory::setCompression);
		map.from(this.serverProperties::getHttp2).to(factory::setHttp2);
		map.from(this.serverProperties::getServerHeader).to(factory::setServerHeader);
		map.from(this.serverProperties.getServlet()::getContextParameters).to(factory::setInitParameters);
		map.from(this.serverProperties.getShutdown()).to(factory::setShutdown);
		for (WebListenerRegistrar registrar : this.webListenerRegistrars) {
			registrar.register(factory);
		}
	}

}
