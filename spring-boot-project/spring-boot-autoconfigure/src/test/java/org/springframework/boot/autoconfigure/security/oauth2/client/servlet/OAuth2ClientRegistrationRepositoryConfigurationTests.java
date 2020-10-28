package org.springframework.boot.autoconfigure.security.oauth2.client.servlet;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OAuth2ClientRegistrationRepositoryConfiguration}.
 *

 */
class OAuth2ClientRegistrationRepositoryConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

	private static final String REGISTRATION_PREFIX = "spring.security.oauth2.client.registration";

	@Test
	void clientRegistrationRepositoryBeanShouldNotBeCreatedWhenPropertiesAbsent() {
		this.contextRunner.withUserConfiguration(OAuth2ClientRegistrationRepositoryConfiguration.class)
				.run((context) -> assertThat(context).doesNotHaveBean(ClientRegistrationRepository.class));
	}

	@Test
	void clientRegistrationRepositoryBeanShouldBeCreatedWhenPropertiesPresent() {
		this.contextRunner.withUserConfiguration(OAuth2ClientRegistrationRepositoryConfiguration.class)
				.withPropertyValues(REGISTRATION_PREFIX + ".foo.client-id=abcd",
						REGISTRATION_PREFIX + ".foo.client-secret=secret", REGISTRATION_PREFIX + ".foo.provider=github")
				.run((context) -> {
					ClientRegistrationRepository repository = context.getBean(ClientRegistrationRepository.class);
					ClientRegistration registration = repository.findByRegistrationId("foo");
					assertThat(registration).isNotNull();
					assertThat(registration.getClientSecret()).isEqualTo("secret");
				});
	}

}
