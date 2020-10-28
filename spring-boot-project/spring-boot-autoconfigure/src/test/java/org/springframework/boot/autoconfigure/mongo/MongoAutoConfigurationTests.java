package org.springframework.boot.autoconfigure.mongo;

import java.util.concurrent.TimeUnit;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MongoAutoConfiguration}.
 *



 */
class MongoAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(MongoAutoConfiguration.class));

	@Test
	void clientExists() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(MongoClient.class));
	}

	@Test
	void settingsAdded() {
		this.contextRunner.withUserConfiguration(SettingsConfig.class)
				.run((context) -> assertThat(
						getSettings(context).getSocketSettings().getConnectTimeout(TimeUnit.MILLISECONDS))
								.isEqualTo(300));
	}

	@Test
	void settingsAddedButNoHost() {
		this.contextRunner.withUserConfiguration(SettingsConfig.class)
				.run((context) -> assertThat(
						getSettings(context).getSocketSettings().getConnectTimeout(TimeUnit.MILLISECONDS))
								.isEqualTo(300));
	}

	@Test
	void settingsSslConfig() {
		this.contextRunner.withUserConfiguration(SslSettingsConfig.class)
				.run((context) -> assertThat(getSettings(context).getSslSettings().isEnabled()).isTrue());
	}

	@Test
	void configuresSingleClient() {
		this.contextRunner.withUserConfiguration(FallbackMongoClientConfig.class)
				.run((context) -> assertThat(context).hasSingleBean(MongoClient.class));
	}

	@Test
	void customizerOverridesAutoConfig() {
		this.contextRunner.withPropertyValues("spring.data.mongodb.uri:mongodb://localhost/test?appname=auto-config")
				.withUserConfiguration(SimpleCustomizerConfig.class)
				.run((context) -> assertThat(getSettings(context).getApplicationName()).isEqualTo("overridden-name"));
	}

	private MongoClientSettings getSettings(AssertableApplicationContext context) {
		assertThat(context).hasSingleBean(MongoClient.class);
		MongoClient client = context.getBean(MongoClient.class);
		return (MongoClientSettings) ReflectionTestUtils.getField(client, "settings");
	}

	@Configuration(proxyBeanMethods = false)
	static class SettingsConfig {

		@Bean
		MongoClientSettings mongoClientSettings() {
			return MongoClientSettings.builder().applyToSocketSettings(
					(socketSettings) -> socketSettings.connectTimeout(300, TimeUnit.MILLISECONDS)).build();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class SslSettingsConfig {

		@Bean
		MongoClientSettings mongoClientSettings() {
			return MongoClientSettings.builder().applyToSslSettings((ssl) -> ssl.enabled(true)).build();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class FallbackMongoClientConfig {

		@Bean
		MongoClient fallbackMongoClient() {
			return MongoClients.create();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class SimpleCustomizerConfig {

		@Bean
		MongoClientSettingsBuilderCustomizer customizer() {
			return (clientSettingsBuilder) -> clientSettingsBuilder.applicationName("overridden-name");
		}

	}

}
