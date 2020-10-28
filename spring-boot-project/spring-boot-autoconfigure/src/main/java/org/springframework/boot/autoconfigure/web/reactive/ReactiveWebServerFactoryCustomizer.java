package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

/**
 * {@link WebServerFactoryCustomizer} to apply {@link ServerProperties} to reactive
 * servers.
 *


 * @since 2.0.0
 */
public class ReactiveWebServerFactoryCustomizer
		implements WebServerFactoryCustomizer<ConfigurableReactiveWebServerFactory>, Ordered {

	private final ServerProperties serverProperties;

	public ReactiveWebServerFactoryCustomizer(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void customize(ConfigurableReactiveWebServerFactory factory) {
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		map.from(this.serverProperties::getPort).to(factory::setPort);
		map.from(this.serverProperties::getAddress).to(factory::setAddress);
		map.from(this.serverProperties::getSsl).to(factory::setSsl);
		map.from(this.serverProperties::getCompression).to(factory::setCompression);
		map.from(this.serverProperties::getHttp2).to(factory::setHttp2);
		map.from(this.serverProperties.getShutdown()).to(factory::setShutdown);
	}

}
