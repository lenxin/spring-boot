package org.springframework.boot.autoconfigure.amqp;

import java.time.Duration;
import java.util.List;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.PropertyMapper;

/**
 * Configure {@link RabbitTemplate} with sensible defaults.
 *

 * @since 2.3.0
 */
public class RabbitTemplateConfigurer {

	private MessageConverter messageConverter;

	private List<RabbitRetryTemplateCustomizer> retryTemplateCustomizers;

	private RabbitProperties rabbitProperties;

	/**
	 * Set the {@link MessageConverter} to use or {@code null} if the out-of-the-box
	 * converter should be used.
	 * @param messageConverter the {@link MessageConverter}
	 */
	protected void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	/**
	 * Set the {@link RabbitRetryTemplateCustomizer} instances to use.
	 * @param retryTemplateCustomizers the retry template customizers
	 */
	protected void setRetryTemplateCustomizers(List<RabbitRetryTemplateCustomizer> retryTemplateCustomizers) {
		this.retryTemplateCustomizers = retryTemplateCustomizers;
	}

	/**
	 * Set the {@link RabbitProperties} to use.
	 * @param rabbitProperties the {@link RabbitProperties}
	 */
	protected void setRabbitProperties(RabbitProperties rabbitProperties) {
		this.rabbitProperties = rabbitProperties;
	}

	protected final RabbitProperties getRabbitProperties() {
		return this.rabbitProperties;
	}

	/**
	 * Configure the specified {@link RabbitTemplate}. The template can be further tuned
	 * and default settings can be overridden.
	 * @param template the {@link RabbitTemplate} instance to configure
	 * @param connectionFactory the {@link ConnectionFactory} to use
	 */
	public void configure(RabbitTemplate template, ConnectionFactory connectionFactory) {
		PropertyMapper map = PropertyMapper.get();
		template.setConnectionFactory(connectionFactory);
		if (this.messageConverter != null) {
			template.setMessageConverter(this.messageConverter);
		}
		template.setMandatory(determineMandatoryFlag());
		RabbitProperties.Template templateProperties = this.rabbitProperties.getTemplate();
		if (templateProperties.getRetry().isEnabled()) {
			template.setRetryTemplate(new RetryTemplateFactory(this.retryTemplateCustomizers)
					.createRetryTemplate(templateProperties.getRetry(), RabbitRetryTemplateCustomizer.Target.SENDER));
		}
		map.from(templateProperties::getReceiveTimeout).whenNonNull().as(Duration::toMillis)
				.to(template::setReceiveTimeout);
		map.from(templateProperties::getReplyTimeout).whenNonNull().as(Duration::toMillis)
				.to(template::setReplyTimeout);
		map.from(templateProperties::getExchange).to(template::setExchange);
		map.from(templateProperties::getRoutingKey).to(template::setRoutingKey);
		map.from(templateProperties::getDefaultReceiveQueue).whenNonNull().to(template::setDefaultReceiveQueue);
	}

	private boolean determineMandatoryFlag() {
		Boolean mandatory = this.rabbitProperties.getTemplate().getMandatory();
		return (mandatory != null) ? mandatory : this.rabbitProperties.isPublisherReturns();
	}

}
