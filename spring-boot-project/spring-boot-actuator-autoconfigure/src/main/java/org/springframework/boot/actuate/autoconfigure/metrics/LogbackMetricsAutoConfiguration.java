package org.springframework.boot.actuate.autoconfigure.metrics;

import ch.qos.logback.classic.LoggerContext;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.logging.LogbackMetrics;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import org.springframework.boot.actuate.autoconfigure.metrics.LogbackMetricsAutoConfiguration.LogbackLoggingCondition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Logback metrics.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class })
@ConditionalOnClass({ MeterRegistry.class, LoggerContext.class, LoggerFactory.class })
@ConditionalOnBean(MeterRegistry.class)
@Conditional(LogbackLoggingCondition.class)
public class LogbackMetricsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public LogbackMetrics logbackMetrics() {
		return new LogbackMetrics();
	}

	static class LogbackLoggingCondition extends SpringBootCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
			ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
			ConditionMessage.Builder message = ConditionMessage.forCondition("LogbackLoggingCondition");
			if (loggerFactory instanceof LoggerContext) {
				return ConditionOutcome.match(message.because("ILoggerFactory is a Logback LoggerContext"));
			}
			return ConditionOutcome.noMatch(
					message.because("ILoggerFactory is an instance of " + loggerFactory.getClass().getCanonicalName()));
		}

	}

}
