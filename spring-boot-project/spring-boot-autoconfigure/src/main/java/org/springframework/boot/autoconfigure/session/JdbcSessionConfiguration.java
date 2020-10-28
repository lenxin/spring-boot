package org.springframework.boot.autoconfigure.session;

import java.time.Duration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.session.SessionRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration;

/**
 * JDBC backed session configuration.
 *



 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ JdbcTemplate.class, JdbcIndexedSessionRepository.class })
@ConditionalOnMissingBean(SessionRepository.class)
@ConditionalOnBean(DataSource.class)
@Conditional(ServletSessionCondition.class)
@EnableConfigurationProperties(JdbcSessionProperties.class)
class JdbcSessionConfiguration {

	@Bean
	@ConditionalOnMissingBean
	JdbcSessionDataSourceInitializer jdbcSessionDataSourceInitializer(DataSource dataSource,
			ResourceLoader resourceLoader, JdbcSessionProperties properties) {
		return new JdbcSessionDataSourceInitializer(dataSource, resourceLoader, properties);
	}

	@Configuration(proxyBeanMethods = false)
	static class SpringBootJdbcHttpSessionConfiguration extends JdbcHttpSessionConfiguration {

		@Autowired
		void customize(SessionProperties sessionProperties, JdbcSessionProperties jdbcSessionProperties,
				ServerProperties serverProperties) {
			Duration timeout = sessionProperties
					.determineTimeout(() -> serverProperties.getServlet().getSession().getTimeout());
			if (timeout != null) {
				setMaxInactiveIntervalInSeconds((int) timeout.getSeconds());
			}
			setTableName(jdbcSessionProperties.getTableName());
			setCleanupCron(jdbcSessionProperties.getCleanupCron());
			setFlushMode(jdbcSessionProperties.getFlushMode());
			setSaveMode(jdbcSessionProperties.getSaveMode());
		}

	}

}
