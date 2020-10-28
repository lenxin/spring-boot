package org.springframework.boot.autoconfigure.session;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.session.web.http.DefaultCookieSerializer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SessionAutoConfiguration} when Spring Security is not on the
 * classpath.
 *

 */
@ClassPathExclusions("spring-security-*")
class SessionAutoConfigurationWithoutSecurityTests extends AbstractSessionAutoConfigurationTests {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(SessionAutoConfiguration.class));

	@Test
	void sessionCookieConfigurationIsAppliedToAutoConfiguredCookieSerializer() {
		this.contextRunner.withUserConfiguration(SessionRepositoryConfiguration.class).run((context) -> {
			DefaultCookieSerializer cookieSerializer = context.getBean(DefaultCookieSerializer.class);
			assertThat(cookieSerializer).hasFieldOrPropertyWithValue("rememberMeRequestAttribute", null);
		});
	}

}
