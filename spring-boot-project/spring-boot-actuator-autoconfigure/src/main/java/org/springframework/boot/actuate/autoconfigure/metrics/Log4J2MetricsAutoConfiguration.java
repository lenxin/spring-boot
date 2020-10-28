package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.logging.Log4j2Metrics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerContext;

import org.springframework.boot.actuate.autoconfigure.metrics.Log4J2MetricsAutoConfiguration.Log4JCoreLoggerContextCondition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
 * Auto-configuration for Log4J2 metrics.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class })
@ConditionalOnClass(value = { Log4j2Metrics.class, LogManager.class },
		name = "org.apache.logging.log4j.core.LoggerContext")
@ConditionalOnBean(MeterRegistry.class)
@Conditional(Log4JCoreLoggerContextCondition.class)
public class Log4J2MetricsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Log4j2Metrics log4j2Metrics() {
		return new Log4j2Metrics();
	}

	static class Log4JCoreLoggerContextCondition extends SpringBootCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
			LoggerContext loggerContext = LogManager.getContext(false);
			try {
				if (Class.forName("org.apache.logging.log4j.core.LoggerContext").isInstance(loggerContext)) {
					return ConditionOutcome
							.match("LoggerContext was an instance of org.apache.logging.log4j.core.LoggerContext");
				}
			}
			catch (Throwable ex) {
				// Continue with no match
			}
			return ConditionOutcome
					.noMatch("Logger context was not an instance of org.apache.logging.log4j.core.LoggerContext");
		}

	}

}
