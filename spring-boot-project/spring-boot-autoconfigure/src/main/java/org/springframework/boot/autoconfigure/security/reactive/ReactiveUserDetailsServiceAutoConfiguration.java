package org.springframework.boot.autoconfigure.security.reactive;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * Default user {@link Configuration @Configuration} for a reactive web application.
 * Configures a {@link ReactiveUserDetailsService} with a default user and generated
 * password. This backs-off completely if there is a bean of type
 * {@link ReactiveUserDetailsService} or {@link ReactiveAuthenticationManager}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ReactiveAuthenticationManager.class })
@ConditionalOnMissingBean(value = { ReactiveAuthenticationManager.class, ReactiveUserDetailsService.class },
		type = { "org.springframework.security.oauth2.jwt.ReactiveJwtDecoder",
				"org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector" })
@Conditional(ReactiveUserDetailsServiceAutoConfiguration.ReactiveUserDetailsServiceCondition.class)
@EnableConfigurationProperties(SecurityProperties.class)
@AutoConfigureAfter(RSocketMessagingAutoConfiguration.class)
public class ReactiveUserDetailsServiceAutoConfiguration {

	private static final String NOOP_PASSWORD_PREFIX = "{noop}";

	private static final Pattern PASSWORD_ALGORITHM_PATTERN = Pattern.compile("^\\{.+}.*$");

	private static final Log logger = LogFactory.getLog(ReactiveUserDetailsServiceAutoConfiguration.class);

	@Bean
	public MapReactiveUserDetailsService reactiveUserDetailsService(SecurityProperties properties,
			ObjectProvider<PasswordEncoder> passwordEncoder) {
		SecurityProperties.User user = properties.getUser();
		UserDetails userDetails = getUserDetails(user, getOrDeducePassword(user, passwordEncoder.getIfAvailable()));
		return new MapReactiveUserDetailsService(userDetails);
	}

	private UserDetails getUserDetails(SecurityProperties.User user, String password) {
		List<String> roles = user.getRoles();
		return User.withUsername(user.getName()).password(password).roles(StringUtils.toStringArray(roles)).build();
	}

	private String getOrDeducePassword(SecurityProperties.User user, PasswordEncoder encoder) {
		String password = user.getPassword();
		if (user.isPasswordGenerated()) {
			logger.info(String.format("%n%nUsing generated security password: %s%n", user.getPassword()));
		}
		if (encoder != null || PASSWORD_ALGORITHM_PATTERN.matcher(password).matches()) {
			return password;
		}
		return NOOP_PASSWORD_PREFIX + password;
	}

	static class ReactiveUserDetailsServiceCondition extends AnyNestedCondition {

		ReactiveUserDetailsServiceCondition() {
			super(ConfigurationPhase.REGISTER_BEAN);
		}

		@ConditionalOnBean(RSocketMessageHandler.class)
		static class RSocketSecurityEnabledCondition {

		}

		@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
		static class ReactiveWebApplicationCondition {

		}

	}

}
