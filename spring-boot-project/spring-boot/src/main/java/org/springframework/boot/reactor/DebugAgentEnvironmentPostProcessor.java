package org.springframework.boot.reactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ClassUtils;

/**
 * {@link EnvironmentPostProcessor} to enable the Reactor Debug Agent if available.
 * <p>
 * The debug agent is enabled by default, unless the
 * {@code "spring.reactor.debug-agent.enabled"} configuration property is set to false. We
 * are using here an {@link EnvironmentPostProcessor} instead of an auto-configuration
 * class to enable the agent as soon as possible during the startup process.
 *

 * @since 2.2.0
 */
public class DebugAgentEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

	private static final String REACTOR_DEBUGAGENT_CLASS = "reactor.tools.agent.ReactorDebugAgent";

	private static final String DEBUGAGENT_ENABLED_CONFIG_KEY = "spring.reactor.debug-agent.enabled";

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		if (ClassUtils.isPresent(REACTOR_DEBUGAGENT_CLASS, null)) {
			Boolean agentEnabled = environment.getProperty(DEBUGAGENT_ENABLED_CONFIG_KEY, Boolean.class);
			if (agentEnabled != Boolean.FALSE) {
				try {
					Class<?> debugAgent = Class.forName(REACTOR_DEBUGAGENT_CLASS);
					debugAgent.getMethod("init").invoke(null);
				}
				catch (Exception ex) {
					throw new RuntimeException("Failed to init Reactor's debug agent", ex);
				}
			}
		}
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

}
