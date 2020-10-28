package org.springframework.boot.autoconfigure.kafka;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Cleanup;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.IsolationLevel;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Listener;
import org.springframework.kafka.core.CleanupConfig;
import org.springframework.kafka.listener.ContainerProperties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link KafkaProperties}.
 *

 */
class KafkaPropertiesTests {

	@Test
	void isolationLevelEnumConsistentWithKafkaVersion() {
		org.apache.kafka.common.IsolationLevel[] original = org.apache.kafka.common.IsolationLevel.values();
		assertThat(original).extracting("name").containsExactly(IsolationLevel.READ_UNCOMMITTED.name(),
				IsolationLevel.READ_COMMITTED.name());
		assertThat(original).extracting("id").containsExactly(IsolationLevel.READ_UNCOMMITTED.id(),
				IsolationLevel.READ_COMMITTED.id());
		assertThat(original).hasSize(IsolationLevel.values().length);
	}

	@Test
	void listenerDefaultValuesAreConsistent() {
		ContainerProperties container = new ContainerProperties("test");
		Listener listenerProperties = new KafkaProperties().getListener();
		assertThat(listenerProperties.isMissingTopicsFatal()).isEqualTo(container.isMissingTopicsFatal());
	}

	@Test
	void cleanupConfigDefaultValuesAreConsistent() {
		CleanupConfig cleanupConfig = new CleanupConfig();
		Cleanup cleanup = new KafkaProperties().getStreams().getCleanup();
		assertThat(cleanup.isOnStartup()).isEqualTo(cleanupConfig.cleanupOnStart());
		assertThat(cleanup.isOnShutdown()).isEqualTo(cleanupConfig.cleanupOnStop());
	}

}
