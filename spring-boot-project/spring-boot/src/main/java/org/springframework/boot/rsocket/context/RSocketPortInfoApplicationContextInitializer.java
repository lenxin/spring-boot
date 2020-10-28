package org.springframework.boot.rsocket.context;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * {@link ApplicationContextInitializer} that sets {@link Environment} properties for the
 * ports that {@link RSocketServer} servers are actually listening on. The property
 * {@literal "local.rsocket.server.port"} can be injected directly into tests using
 * {@link Value @Value} or obtained via the {@link Environment}.
 * <p>
 * Properties are automatically propagated up to any parent context.
 *


 * @since 2.2.0
 */
public class RSocketPortInfoApplicationContextInitializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		applicationContext.addApplicationListener(new Listener(applicationContext));
	}

	private static class Listener implements ApplicationListener<RSocketServerInitializedEvent> {

		private static final String PROPERTY_NAME = "local.rsocket.server.port";

		private final ConfigurableApplicationContext applicationContext;

		Listener(ConfigurableApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}

		@Override
		public void onApplicationEvent(RSocketServerInitializedEvent event) {
			if (event.getServer().address() != null) {
				setPortProperty(this.applicationContext, event.getServer().address().getPort());
			}
		}

		private void setPortProperty(ApplicationContext context, int port) {
			if (context instanceof ConfigurableApplicationContext) {
				setPortProperty(((ConfigurableApplicationContext) context).getEnvironment(), port);
			}
			if (context.getParent() != null) {
				setPortProperty(context.getParent(), port);
			}
		}

		private void setPortProperty(ConfigurableEnvironment environment, int port) {
			MutablePropertySources sources = environment.getPropertySources();
			PropertySource<?> source = sources.get("server.ports");
			if (source == null) {
				source = new MapPropertySource("server.ports", new HashMap<>());
				sources.addFirst(source);
			}
			setPortProperty(port, source);
		}

		@SuppressWarnings("unchecked")
		private void setPortProperty(int port, PropertySource<?> source) {
			((Map<String, Object>) source.getSource()).put(PROPERTY_NAME, port);
		}

	}

}
