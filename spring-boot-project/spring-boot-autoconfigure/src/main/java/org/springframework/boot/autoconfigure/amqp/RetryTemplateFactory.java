package org.springframework.boot.autoconfigure.amqp;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * Factory to create {@link RetryTemplate} instance from properties defined in
 * {@link RabbitProperties}.
 *

 */
class RetryTemplateFactory {

	private final List<RabbitRetryTemplateCustomizer> customizers;

	RetryTemplateFactory(List<RabbitRetryTemplateCustomizer> customizers) {
		this.customizers = customizers;
	}

	RetryTemplate createRetryTemplate(RabbitProperties.Retry properties, RabbitRetryTemplateCustomizer.Target target) {
		PropertyMapper map = PropertyMapper.get();
		RetryTemplate template = new RetryTemplate();
		SimpleRetryPolicy policy = new SimpleRetryPolicy();
		map.from(properties::getMaxAttempts).to(policy::setMaxAttempts);
		template.setRetryPolicy(policy);
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		map.from(properties::getInitialInterval).whenNonNull().as(Duration::toMillis)
				.to(backOffPolicy::setInitialInterval);
		map.from(properties::getMultiplier).to(backOffPolicy::setMultiplier);
		map.from(properties::getMaxInterval).whenNonNull().as(Duration::toMillis).to(backOffPolicy::setMaxInterval);
		template.setBackOffPolicy(backOffPolicy);
		if (this.customizers != null) {
			for (RabbitRetryTemplateCustomizer customizer : this.customizers) {
				customizer.customize(target, template);
			}
		}
		return template;
	}

}
