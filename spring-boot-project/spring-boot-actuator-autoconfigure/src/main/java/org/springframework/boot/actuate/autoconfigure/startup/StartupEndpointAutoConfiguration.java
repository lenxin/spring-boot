package org.springframework.boot.actuate.autoconfigure.startup;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.startup.StartupEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the {@link StartupEndpoint}.
 *

 * @since 2.4.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = StartupEndpoint.class)
@Conditional(StartupEndpointAutoConfiguration.ApplicationStartupCondition.class)
public class StartupEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public StartupEndpoint startupEndpoint(BufferingApplicationStartup applicationStartup) {
		return new StartupEndpoint(applicationStartup);
	}

	/**
	 * {@link SpringBootCondition} checking the configured
	 * {@link org.springframework.core.metrics.ApplicationStartup}.
	 * <p>
	 * Endpoint is enabled only if the configured implementation is
	 * {@link BufferingApplicationStartup}.
	 */
	static class ApplicationStartupCondition extends SpringBootCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage.forCondition("ApplicationStartup");
			ApplicationStartup applicationStartup = context.getBeanFactory().getApplicationStartup();
			if (applicationStartup instanceof BufferingApplicationStartup) {
				return ConditionOutcome.match(
						message.because("configured applicationStartup is of type BufferingApplicationStartup."));
			}
			return ConditionOutcome.noMatch(message.because("configured applicationStartup is of type "
					+ applicationStartup.getClass() + ", expected BufferingApplicationStartup."));
		}

	}

}
