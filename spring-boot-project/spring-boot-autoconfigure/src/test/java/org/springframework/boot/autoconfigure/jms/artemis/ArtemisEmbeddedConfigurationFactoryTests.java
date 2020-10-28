package org.springframework.boot.autoconfigure.jms.artemis;

import java.util.List;
import java.util.Map;

import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.CoreAddressConfiguration;
import org.apache.activemq.artemis.core.server.JournalType;
import org.apache.activemq.artemis.core.settings.impl.AddressSettings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ArtemisEmbeddedConfigurationFactory}
 *



 */
class ArtemisEmbeddedConfigurationFactoryTests {

	@Test
	void defaultDataDir() {
		ArtemisProperties properties = new ArtemisProperties();
		properties.getEmbedded().setPersistent(true);
		Configuration configuration = new ArtemisEmbeddedConfigurationFactory(properties).createConfiguration();
		assertThat(configuration.getJournalDirectory()).startsWith(System.getProperty("java.io.tmpdir"))
				.endsWith("/journal");
	}

	@Test
	void persistenceSetup() {
		ArtemisProperties properties = new ArtemisProperties();
		properties.getEmbedded().setPersistent(true);
		Configuration configuration = new ArtemisEmbeddedConfigurationFactory(properties).createConfiguration();
		assertThat(configuration.isPersistenceEnabled()).isTrue();
		assertThat(configuration.getJournalType()).isEqualTo(JournalType.NIO);
	}

	@Test
	void generatedClusterPassword() {
		ArtemisProperties properties = new ArtemisProperties();
		Configuration configuration = new ArtemisEmbeddedConfigurationFactory(properties).createConfiguration();
		assertThat(configuration.getClusterPassword()).hasSize(36);
	}

	@Test
	void specificClusterPassword() {
		ArtemisProperties properties = new ArtemisProperties();
		properties.getEmbedded().setClusterPassword("password");
		Configuration configuration = new ArtemisEmbeddedConfigurationFactory(properties).createConfiguration();
		assertThat(configuration.getClusterPassword()).isEqualTo("password");
	}

	@Test
	void hasDlqExpiryQueueAddressSettingsConfigured() {
		ArtemisProperties properties = new ArtemisProperties();
		Configuration configuration = new ArtemisEmbeddedConfigurationFactory(properties).createConfiguration();
		Map<String, AddressSettings> addressesSettings = configuration.getAddressesSettings();
		assertThat((Object) addressesSettings.get("#").getDeadLetterAddress())
				.isEqualTo(SimpleString.toSimpleString("DLQ"));
		assertThat((Object) addressesSettings.get("#").getExpiryAddress())
				.isEqualTo(SimpleString.toSimpleString("ExpiryQueue"));
	}

	@Test
	void hasDlqExpiryQueueConfigured() {
		ArtemisProperties properties = new ArtemisProperties();
		Configuration configuration = new ArtemisEmbeddedConfigurationFactory(properties).createConfiguration();
		List<CoreAddressConfiguration> addressConfigurations = configuration.getAddressConfigurations();
		assertThat(addressConfigurations).hasSize(2);
	}

}
