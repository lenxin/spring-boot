package org.springframework.boot.autoconfigure.orm.jpa;

import javax.persistence.EntityManager;

import org.hibernate.engine.spi.SessionImplementor;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Hibernate JPA.
 *




 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ LocalContainerEntityManagerFactoryBean.class, EntityManager.class, SessionImplementor.class })
@EnableConfigurationProperties(JpaProperties.class)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class })
@Import(HibernateJpaConfiguration.class)
public class HibernateJpaAutoConfiguration {

}
