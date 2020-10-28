package org.springframework.boot.actuate.autoconfigure.condition;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the
 * {@link ConditionsReportEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = ConditionsReportEndpoint.class)
public class ConditionsReportEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
	public ConditionsReportEndpoint conditionsReportEndpoint(ConfigurableApplicationContext context) {
		return new ConditionsReportEndpoint(context);
	}

}
