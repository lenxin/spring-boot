package org.springframework.boot.actuate.autoconfigure.session;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.session.SessionsEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link SessionsEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(FindByIndexNameSessionRepository.class)
@ConditionalOnAvailableEndpoint(endpoint = SessionsEndpoint.class)
@AutoConfigureAfter(SessionAutoConfiguration.class)
public class SessionsEndpointAutoConfiguration {

	@Bean
	@ConditionalOnBean(FindByIndexNameSessionRepository.class)
	@ConditionalOnMissingBean
	public SessionsEndpoint sessionEndpoint(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
		return new SessionsEndpoint(sessionRepository);
	}

}
