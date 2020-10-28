package org.springframework.boot.actuate.autoconfigure.audit;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.listener.AbstractAuditListener;
import org.springframework.boot.actuate.audit.listener.AuditListener;
import org.springframework.boot.actuate.security.AbstractAuthenticationAuditListener;
import org.springframework.boot.actuate.security.AbstractAuthorizationAuditListener;
import org.springframework.boot.actuate.security.AuthenticationAuditListener;
import org.springframework.boot.actuate.security.AuthorizationAuditListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link AuditEvent}s.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(AuditEventRepository.class)
@ConditionalOnProperty(prefix = "management.auditevents", name = "enabled", matchIfMissing = true)
public class AuditAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(AbstractAuditListener.class)
	public AuditListener auditListener(AuditEventRepository auditEventRepository) {
		return new AuditListener(auditEventRepository);
	}

	@Bean
	@ConditionalOnClass(name = "org.springframework.security.authentication.event.AbstractAuthenticationEvent")
	@ConditionalOnMissingBean(AbstractAuthenticationAuditListener.class)
	public AuthenticationAuditListener authenticationAuditListener() throws Exception {
		return new AuthenticationAuditListener();
	}

	@Bean
	@ConditionalOnClass(name = "org.springframework.security.access.event.AbstractAuthorizationEvent")
	@ConditionalOnMissingBean(AbstractAuthorizationAuditListener.class)
	public AuthorizationAuditListener authorizationAuditListener() throws Exception {
		return new AuthorizationAuditListener();
	}

}
