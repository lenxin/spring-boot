package org.springframework.boot.autoconfigure.security.oauth2.client.reactive;

import reactor.core.publisher.Flux;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security's Reactive
 * OAuth2 client.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(ReactiveSecurityAutoConfiguration.class)
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@Conditional(ReactiveOAuth2ClientAutoConfiguration.NonServletApplicationCondition.class)
@ConditionalOnClass({ Flux.class, EnableWebFluxSecurity.class, ClientRegistration.class })
@Import({ ReactiveOAuth2ClientConfigurations.ReactiveClientRegistrationRepositoryConfiguration.class,
		ReactiveOAuth2ClientConfigurations.ReactiveOAuth2ClientConfiguration.class })
public class ReactiveOAuth2ClientAutoConfiguration {

	static class NonServletApplicationCondition extends NoneNestedConditions {

		NonServletApplicationCondition() {
			super(ConfigurationPhase.PARSE_CONFIGURATION);
		}

		@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
		static class ServletApplicationCondition {

		}

	}

}
