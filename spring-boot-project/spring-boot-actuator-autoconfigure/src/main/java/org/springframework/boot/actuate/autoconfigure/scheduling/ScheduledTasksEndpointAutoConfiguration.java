package org.springframework.boot.actuate.autoconfigure.scheduling;

import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.scheduling.ScheduledTasksEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.ScheduledTaskHolder;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link ScheduledTasksEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = ScheduledTasksEndpoint.class)
public class ScheduledTasksEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ScheduledTasksEndpoint scheduledTasksEndpoint(ObjectProvider<ScheduledTaskHolder> holders) {
		return new ScheduledTasksEndpoint(holders.orderedStream().collect(Collectors.toList()));
	}

}
