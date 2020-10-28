package org.springframework.boot.actuate.autoconfigure.audit;

import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.AuditEventsEndpoint;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the {@link AuditEventsEndpoint}.
 *



 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(AuditAutoConfiguration.class)
@ConditionalOnAvailableEndpoint(endpoint = AuditEventsEndpoint.class)
public class AuditEventsEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(AuditEventRepository.class)
	public AuditEventsEndpoint auditEventsEndpoint(AuditEventRepository auditEventRepository) {
		return new AuditEventsEndpoint(auditEventRepository);
	}

}
