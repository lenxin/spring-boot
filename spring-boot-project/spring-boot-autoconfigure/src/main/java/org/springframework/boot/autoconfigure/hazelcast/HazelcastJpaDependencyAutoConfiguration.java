package org.springframework.boot.autoconfigure.hazelcast;

import javax.persistence.EntityManagerFactory;

import com.hazelcast.core.HazelcastInstance;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.EntityManagerFactoryDependsOnPostProcessor;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration.HazelcastInstanceEntityManagerFactoryDependsOnPostProcessor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * Additional configuration to ensure that {@link EntityManagerFactory} beans depend on
 * the {@code hazelcastInstance} bean.
 *

 * @since 1.3.2
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ HazelcastInstance.class, LocalContainerEntityManagerFactoryBean.class })
@AutoConfigureAfter({ HazelcastAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@Import(HazelcastInstanceEntityManagerFactoryDependsOnPostProcessor.class)
public class HazelcastJpaDependencyAutoConfiguration {

	@Conditional(OnHazelcastAndJpaCondition.class)
	static class HazelcastInstanceEntityManagerFactoryDependsOnPostProcessor
			extends EntityManagerFactoryDependsOnPostProcessor {

		HazelcastInstanceEntityManagerFactoryDependsOnPostProcessor() {
			super("hazelcastInstance");
		}

	}

	static class OnHazelcastAndJpaCondition extends AllNestedConditions {

		OnHazelcastAndJpaCondition() {
			super(ConfigurationPhase.REGISTER_BEAN);
		}

		@ConditionalOnBean(name = "hazelcastInstance")
		static class HasHazelcastInstance {

		}

		@ConditionalOnBean(AbstractEntityManagerFactoryBean.class)
		static class HasJpa {

		}

	}

}
