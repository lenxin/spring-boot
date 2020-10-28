package org.springframework.boot.actuate.autoconfigure.hazelcast;

import java.util.Map;

import com.hazelcast.core.HazelcastInstance;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.hazelcast.HazelcastHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link HazelcastHealthIndicator}.
 *

 * @since 2.2.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(HazelcastInstance.class)
@ConditionalOnBean(HazelcastInstance.class)
@ConditionalOnEnabledHealthIndicator("hazelcast")
@AutoConfigureAfter(HazelcastAutoConfiguration.class)
public class HazelcastHealthContributorAutoConfiguration
		extends CompositeHealthContributorConfiguration<HazelcastHealthIndicator, HazelcastInstance> {

	@Bean
	@ConditionalOnMissingBean(name = { "hazelcastHealthIndicator", "hazelcastHealthContributor" })
	public HealthContributor hazelcastHealthContributor(Map<String, HazelcastInstance> hazelcastInstances) {
		return createContributor(hazelcastInstances);
	}

}
