package org.springframework.boot.docs.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.docs.autoconfigure.UserServiceAutoConfiguration.UserProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sample auto-configuration.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(UserService.class)
@EnableConfigurationProperties(UserProperties.class)
public class UserServiceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UserService userService(UserProperties properties) {
		return new UserService(properties.getName());
	}

	@ConfigurationProperties("user")
	public static class UserProperties {

		private String name = "test";

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
