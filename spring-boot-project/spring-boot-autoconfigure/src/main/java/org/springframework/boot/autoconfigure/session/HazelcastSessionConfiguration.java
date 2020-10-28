package org.springframework.boot.autoconfigure.session;

import java.time.Duration;

import com.hazelcast.core.HazelcastInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.config.annotation.web.http.HazelcastHttpSessionConfiguration;

/**
 * Hazelcast backed session configuration.
 *




 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(HazelcastIndexedSessionRepository.class)
@ConditionalOnMissingBean(SessionRepository.class)
@ConditionalOnBean(HazelcastInstance.class)
@Conditional(ServletSessionCondition.class)
@EnableConfigurationProperties(HazelcastSessionProperties.class)
class HazelcastSessionConfiguration {

	@Configuration(proxyBeanMethods = false)
	public static class SpringBootHazelcastHttpSessionConfiguration extends HazelcastHttpSessionConfiguration {

		@Autowired
		public void customize(SessionProperties sessionProperties,
				HazelcastSessionProperties hazelcastSessionProperties, ServerProperties serverProperties) {
			Duration timeout = sessionProperties
					.determineTimeout(() -> serverProperties.getServlet().getSession().getTimeout());
			if (timeout != null) {
				setMaxInactiveIntervalInSeconds((int) timeout.getSeconds());
			}
			setSessionMapName(hazelcastSessionProperties.getMapName());
			setFlushMode(hazelcastSessionProperties.getFlushMode());
			setSaveMode(hazelcastSessionProperties.getSaveMode());
		}

	}

}
