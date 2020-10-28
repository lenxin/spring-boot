package org.springframework.boot.actuate.autoconfigure.session;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.session.SessionsEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FindByIndexNameSessionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link SessionsEndpointAutoConfiguration}.
 *

 */
class SessionsEndpointAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(SessionsEndpointAutoConfiguration.class))
			.withUserConfiguration(SessionConfiguration.class);

	@Test
	void runShouldHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoints.web.exposure.include=sessions")
				.run((context) -> assertThat(context).hasSingleBean(SessionsEndpoint.class));
	}

	@Test
	void runWhenNotExposedShouldNotHaveEndpointBean() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(SessionsEndpoint.class));
	}

	@Test
	void runWhenEnabledPropertyIsFalseShouldNotHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoint.sessions.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(SessionsEndpoint.class));
	}

	@Configuration(proxyBeanMethods = false)
	static class SessionConfiguration {

		@Bean
		FindByIndexNameSessionRepository<?> sessionRepository() {
			return mock(FindByIndexNameSessionRepository.class);
		}

	}

}
