package org.springframework.boot.test.autoconfigure.web.reactive.webclient;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.AutoConfigurationImportedCondition.importedAutoConfiguration;

/**
 * Tests for the auto-configuration imported by {@link WebFluxTest @WebFluxTest}.
 *




 */
@WebFluxTest
class WebFluxTestAutoConfigurationIntegrationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void messageSourceAutoConfigurationIsImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(MessageSourceAutoConfiguration.class));
	}

	@Test
	void validationAutoConfigurationIsImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(ValidationAutoConfiguration.class));
	}

	@Test
	void mustacheAutoConfigurationIsImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(MustacheAutoConfiguration.class));
	}

	@Test
	void freeMarkerAutoConfigurationIsImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(FreeMarkerAutoConfiguration.class));
	}

	@Test
	void thymeleafAutoConfigurationIsImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(ThymeleafAutoConfiguration.class));
	}

	@Test
	void errorWebFluxAutoConfigurationIsImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(ErrorWebFluxAutoConfiguration.class));
	}

	@Test
	void oAuth2ClientAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(ReactiveOAuth2ClientAutoConfiguration.class));
	}

	@Test
	void oAuth2ResourceServerAutoConfigurationWasImported() {
		assertThat(this.applicationContext)
				.has(importedAutoConfiguration(ReactiveOAuth2ResourceServerAutoConfiguration.class));
	}

}
