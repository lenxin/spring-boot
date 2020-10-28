package org.springframework.boot.actuate.autoconfigure.logging;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.logging.LoggerGroups;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the {@link LoggersEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = LoggersEndpoint.class)
public class LoggersEndpointAutoConfiguration {

	@Bean
	@ConditionalOnBean(LoggingSystem.class)
	@Conditional(OnEnabledLoggingSystemCondition.class)
	@ConditionalOnMissingBean
	public LoggersEndpoint loggersEndpoint(LoggingSystem loggingSystem,
			ObjectProvider<LoggerGroups> springBootLoggerGroups) {
		return new LoggersEndpoint(loggingSystem, springBootLoggerGroups.getIfAvailable(LoggerGroups::new));
	}

	static class OnEnabledLoggingSystemCondition extends SpringBootCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage.forCondition("Logging System");
			String loggingSystem = System.getProperty(LoggingSystem.SYSTEM_PROPERTY);
			if (LoggingSystem.NONE.equals(loggingSystem)) {
				return ConditionOutcome.noMatch(
						message.because("system property " + LoggingSystem.SYSTEM_PROPERTY + " is set to none"));
			}
			return ConditionOutcome.match(message.because("enabled"));
		}

	}

}
