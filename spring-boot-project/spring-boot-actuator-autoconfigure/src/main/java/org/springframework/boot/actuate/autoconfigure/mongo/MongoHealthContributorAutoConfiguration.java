package org.springframework.boot.actuate.autoconfigure.mongo;

import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.mongo.MongoHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link MongoHealthIndicator}.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MongoTemplate.class)
@ConditionalOnBean(MongoTemplate.class)
@ConditionalOnEnabledHealthIndicator("mongo")
@AutoConfigureAfter({ MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
		MongoReactiveHealthContributorAutoConfiguration.class })
public class MongoHealthContributorAutoConfiguration
		extends CompositeHealthContributorConfiguration<MongoHealthIndicator, MongoTemplate> {

	@Bean
	@ConditionalOnMissingBean(name = { "mongoHealthIndicator", "mongoHealthContributor" })
	public HealthContributor mongoHealthContributor(Map<String, MongoTemplate> mongoTemplates) {
		return createContributor(mongoTemplates);
	}

}
