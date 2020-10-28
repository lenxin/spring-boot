package org.springframework.boot.actuate.autoconfigure.management;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.management.ThreadDumpEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the {@link ThreadDumpEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = ThreadDumpEndpoint.class)
public class ThreadDumpEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ThreadDumpEndpoint dumpEndpoint() {
		return new ThreadDumpEndpoint();
	}

}
