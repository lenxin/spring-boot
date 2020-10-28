package org.springframework.boot.actuate.autoconfigure.mail;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.mail.MailHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MailHealthContributorAutoConfiguration}.
 *

 */
class MailHealthContributorAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(MailSenderAutoConfiguration.class,
					MailHealthContributorAutoConfiguration.class, HealthContributorAutoConfiguration.class))
			.withPropertyValues("spring.mail.host:smtp.example.com");

	@Test
	void runShouldCreateIndicator() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(MailHealthIndicator.class));
	}

	@Test
	void runWhenDisabledShouldNotCreateIndicator() {
		this.contextRunner.withPropertyValues("management.health.mail.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(MailHealthIndicator.class));
	}

}
