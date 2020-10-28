package org.springframework.boot.autoconfigure.sendgrid;

import com.sendgrid.SendGrid;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link SendGridAutoConfiguration}.
 *


 */
class SendGridAutoConfigurationTests {

	private AnnotationConfigApplicationContext context;

	@AfterEach
	void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	void expectedSendGridBeanCreatedApiKey() {
		loadContext("spring.sendgrid.api-key:SG.SECRET-API-KEY");
		SendGrid sendGrid = this.context.getBean(SendGrid.class);
		assertThat(sendGrid.getRequestHeaders().get("Authorization")).isEqualTo("Bearer SG.SECRET-API-KEY");
	}

	@Test
	void autoConfigurationNotFiredWhenPropertiesNotSet() {
		loadContext();
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.context.getBean(SendGrid.class));
	}

	@Test
	void autoConfigurationNotFiredWhenBeanAlreadyCreated() {
		loadContext(ManualSendGridConfiguration.class, "spring.sendgrid.api-key:SG.SECRET-API-KEY");
		SendGrid sendGrid = this.context.getBean(SendGrid.class);
		assertThat(sendGrid.getRequestHeaders().get("Authorization")).isEqualTo("Bearer SG.CUSTOM_API_KEY");
	}

	@Test
	void expectedSendGridBeanWithProxyCreated() {
		loadContext("spring.sendgrid.api-key:SG.SECRET-API-KEY", "spring.sendgrid.proxy.host:localhost",
				"spring.sendgrid.proxy.port:5678");
		SendGrid sendGrid = this.context.getBean(SendGrid.class);
		assertThat(sendGrid).extracting("client").extracting("httpClient").extracting("routePlanner")
				.isInstanceOf(DefaultProxyRoutePlanner.class);
	}

	private void loadContext(String... environment) {
		loadContext(null, environment);
	}

	private void loadContext(Class<?> additionalConfiguration, String... environment) {
		this.context = new AnnotationConfigApplicationContext();
		TestPropertyValues.of(environment).applyTo(this.context);
		ConfigurationPropertySources.attach(this.context.getEnvironment());
		this.context.register(SendGridAutoConfiguration.class);
		if (additionalConfiguration != null) {
			this.context.register(additionalConfiguration);
		}
		this.context.refresh();
	}

	@Configuration(proxyBeanMethods = false)
	static class ManualSendGridConfiguration {

		@Bean
		SendGrid sendGrid() {
			return new SendGrid("SG.CUSTOM_API_KEY", true);
		}

	}

}
