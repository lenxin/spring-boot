package org.springframework.boot.actuate.autoconfigure.management;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.management.HeapDumpWebEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link HeapDumpWebEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = HeapDumpWebEndpoint.class)
public class HeapDumpWebEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public HeapDumpWebEndpoint heapDumpWebEndpoint() {
		return new HeapDumpWebEndpoint();
	}

}
