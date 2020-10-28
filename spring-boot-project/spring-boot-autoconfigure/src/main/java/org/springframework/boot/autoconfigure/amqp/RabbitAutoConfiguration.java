package org.springframework.boot.autoconfigure.amqp;

import java.time.Duration;
import java.util.stream.Collectors;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.CredentialsProvider;
import com.rabbitmq.client.impl.CredentialsRefreshService;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link RabbitTemplate}.
 * <p>
 * This configuration class is active only when the RabbitMQ and Spring AMQP client
 * libraries are on the classpath.
 * <p>
 * Registers the following beans:
 * <ul>
 * <li>{@link org.springframework.amqp.rabbit.core.RabbitTemplate RabbitTemplate} if there
 * is no other bean of the same type in the context.</li>
 * <li>{@link org.springframework.amqp.rabbit.connection.CachingConnectionFactory
 * CachingConnectionFactory} instance if there is no other bean of the same type in the
 * context.</li>
 * <li>{@link org.springframework.amqp.core.AmqpAdmin } instance as long as
 * {@literal spring.rabbitmq.dynamic=true}.</li>
 * </ul>
 * <p>
 * The {@link org.springframework.amqp.rabbit.connection.CachingConnectionFactory} honors
 * the following properties:
 * <ul>
 * <li>{@literal spring.rabbitmq.port} is used to specify the port to which the client
 * should connect, and defaults to 5672.</li>
 * <li>{@literal spring.rabbitmq.username} is used to specify the (optional) username.
 * </li>
 * <li>{@literal spring.rabbitmq.password} is used to specify the (optional) password.
 * </li>
 * <li>{@literal spring.rabbitmq.host} is used to specify the host, and defaults to
 * {@literal localhost}.</li>
 * <li>{@literal spring.rabbitmq.virtualHost} is used to specify the (optional) virtual
 * host to which the client should connect.</li>
 * </ul>
 *






 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ RabbitTemplate.class, Channel.class })
@EnableConfigurationProperties(RabbitProperties.class)
@Import(RabbitAnnotationDrivenConfiguration.class)
public class RabbitAutoConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(ConnectionFactory.class)
	protected static class RabbitConnectionFactoryCreator {

		@Bean
		public CachingConnectionFactory rabbitConnectionFactory(RabbitProperties properties,
				ResourceLoader resourceLoader, ObjectProvider<CredentialsProvider> credentialsProvider,
				ObjectProvider<CredentialsRefreshService> credentialsRefreshService,
				ObjectProvider<ConnectionNameStrategy> connectionNameStrategy) throws Exception {
			CachingConnectionFactory factory = new CachingConnectionFactory(getRabbitConnectionFactoryBean(properties,
					resourceLoader, credentialsProvider, credentialsRefreshService).getObject());
			PropertyMapper map = PropertyMapper.get();
			map.from(properties::determineAddresses).to(factory::setAddresses);
			map.from(properties::getAddressShuffleMode).whenNonNull().to(factory::setAddressShuffleMode);
			map.from(properties::isPublisherReturns).to(factory::setPublisherReturns);
			map.from(properties::getPublisherConfirmType).whenNonNull().to(factory::setPublisherConfirmType);
			RabbitProperties.Cache.Channel channel = properties.getCache().getChannel();
			map.from(channel::getSize).whenNonNull().to(factory::setChannelCacheSize);
			map.from(channel::getCheckoutTimeout).whenNonNull().as(Duration::toMillis)
					.to(factory::setChannelCheckoutTimeout);
			RabbitProperties.Cache.Connection connection = properties.getCache().getConnection();
			map.from(connection::getMode).whenNonNull().to(factory::setCacheMode);
			map.from(connection::getSize).whenNonNull().to(factory::setConnectionCacheSize);
			map.from(connectionNameStrategy::getIfUnique).whenNonNull().to(factory::setConnectionNameStrategy);
			return factory;
		}

		private RabbitConnectionFactoryBean getRabbitConnectionFactoryBean(RabbitProperties properties,
				ResourceLoader resourceLoader, ObjectProvider<CredentialsProvider> credentialsProvider,
				ObjectProvider<CredentialsRefreshService> credentialsRefreshService) {
			RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
			factory.setResourceLoader(resourceLoader);
			PropertyMapper map = PropertyMapper.get();
			map.from(properties::determineHost).whenNonNull().to(factory::setHost);
			map.from(properties::determinePort).to(factory::setPort);
			map.from(properties::determineUsername).whenNonNull().to(factory::setUsername);
			map.from(properties::determinePassword).whenNonNull().to(factory::setPassword);
			map.from(properties::determineVirtualHost).whenNonNull().to(factory::setVirtualHost);
			map.from(properties::getRequestedHeartbeat).whenNonNull().asInt(Duration::getSeconds)
					.to(factory::setRequestedHeartbeat);
			map.from(properties::getRequestedChannelMax).to(factory::setRequestedChannelMax);
			RabbitProperties.Ssl ssl = properties.getSsl();
			if (ssl.determineEnabled()) {
				factory.setUseSSL(true);
				map.from(ssl::getAlgorithm).whenNonNull().to(factory::setSslAlgorithm);
				map.from(ssl::getKeyStoreType).to(factory::setKeyStoreType);
				map.from(ssl::getKeyStore).to(factory::setKeyStore);
				map.from(ssl::getKeyStorePassword).to(factory::setKeyStorePassphrase);
				map.from(ssl::getTrustStoreType).to(factory::setTrustStoreType);
				map.from(ssl::getTrustStore).to(factory::setTrustStore);
				map.from(ssl::getTrustStorePassword).to(factory::setTrustStorePassphrase);
				map.from(ssl::isValidateServerCertificate)
						.to((validate) -> factory.setSkipServerCertificateValidation(!validate));
				map.from(ssl::getVerifyHostname).to(factory::setEnableHostnameVerification);
			}
			map.from(properties::getConnectionTimeout).whenNonNull().asInt(Duration::toMillis)
					.to(factory::setConnectionTimeout);
			map.from(properties::getChannelRpcTimeout).whenNonNull().asInt(Duration::toMillis)
					.to(factory::setChannelRpcTimeout);
			map.from(credentialsProvider::getIfUnique).whenNonNull().to(factory::setCredentialsProvider);
			map.from(credentialsRefreshService::getIfUnique).whenNonNull().to(factory::setCredentialsRefreshService);
			factory.afterPropertiesSet();
			return factory;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(RabbitConnectionFactoryCreator.class)
	protected static class RabbitTemplateConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public RabbitTemplateConfigurer rabbitTemplateConfigurer(RabbitProperties properties,
				ObjectProvider<MessageConverter> messageConverter,
				ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers) {
			RabbitTemplateConfigurer configurer = new RabbitTemplateConfigurer();
			configurer.setMessageConverter(messageConverter.getIfUnique());
			configurer
					.setRetryTemplateCustomizers(retryTemplateCustomizers.orderedStream().collect(Collectors.toList()));
			configurer.setRabbitProperties(properties);
			return configurer;
		}

		@Bean
		@ConditionalOnSingleCandidate(ConnectionFactory.class)
		@ConditionalOnMissingBean(RabbitOperations.class)
		public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
			RabbitTemplate template = new RabbitTemplate();
			configurer.configure(template, connectionFactory);
			return template;
		}

		@Bean
		@ConditionalOnSingleCandidate(ConnectionFactory.class)
		@ConditionalOnProperty(prefix = "spring.rabbitmq", name = "dynamic", matchIfMissing = true)
		@ConditionalOnMissingBean
		public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
			return new RabbitAdmin(connectionFactory);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(RabbitMessagingTemplate.class)
	@ConditionalOnMissingBean(RabbitMessagingTemplate.class)
	@Import(RabbitTemplateConfiguration.class)
	protected static class MessagingTemplateConfiguration {

		@Bean
		@ConditionalOnSingleCandidate(RabbitTemplate.class)
		public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
			return new RabbitMessagingTemplate(rabbitTemplate);
		}

	}

}
