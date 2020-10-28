package org.springframework.boot.actuate.autoconfigure.metrics.amqp;

import com.rabbitmq.client.ConnectionFactory;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for metrics on all available
 * {@link ConnectionFactory connection factories}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, RabbitAutoConfiguration.class,
		SimpleMetricsExportAutoConfiguration.class })
@ConditionalOnClass({ ConnectionFactory.class, AbstractConnectionFactory.class })
@ConditionalOnBean({ AbstractConnectionFactory.class, MeterRegistry.class })
public class RabbitMetricsAutoConfiguration {

	@Bean
	public static RabbitConnectionFactoryMetricsPostProcessor rabbitConnectionFactoryMetricsPostProcessor(
			ApplicationContext applicationContext) {
		return new RabbitConnectionFactoryMetricsPostProcessor(applicationContext);
	}

}
