package org.springframework.boot.actuate.autoconfigure.mongo;

import java.util.Map;

import reactor.core.publisher.Flux;

import org.springframework.boot.actuate.autoconfigure.health.CompositeReactiveHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.mongo.MongoReactiveHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link MongoReactiveHealthIndicator}.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ReactiveMongoTemplate.class, Flux.class })
@ConditionalOnBean(ReactiveMongoTemplate.class)
@ConditionalOnEnabledHealthIndicator("mongo")
@AutoConfigureAfter(MongoReactiveDataAutoConfiguration.class)
public class MongoReactiveHealthContributorAutoConfiguration
		extends CompositeReactiveHealthContributorConfiguration<MongoReactiveHealthIndicator, ReactiveMongoTemplate> {

	@Bean
	@ConditionalOnMissingBean(name = { "mongoHealthIndicator", "mongoHealthContributor" })
	public ReactiveHealthContributor mongoHealthContributor(Map<String, ReactiveMongoTemplate> reactiveMongoTemplates) {
		return createContributor(reactiveMongoTemplates);
	}

}
