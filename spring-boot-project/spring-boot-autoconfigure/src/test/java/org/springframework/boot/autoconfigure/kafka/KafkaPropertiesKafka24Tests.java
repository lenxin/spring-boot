package org.springframework.boot.autoconfigure.kafka;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.IsolationLevel;
import org.springframework.boot.testsupport.classpath.ClassPathOverrides;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link KafkaProperties} with Kafka 2.4.
 *

 */
@ClassPathOverrides("org.apache.kafka:kafka-clients:2.4.1")
class KafkaPropertiesKafka24Tests {

	@Test
	void isolationLevelEnumConsistentWithKafkaVersion() throws ClassNotFoundException {
		Class<?> isolationLevelClass = Class.forName("org.apache.kafka.common.requests.IsolationLevel");
		Object[] original = ReflectionTestUtils.invokeMethod(isolationLevelClass, "values");
		assertThat(original).extracting("name").containsExactly(IsolationLevel.READ_UNCOMMITTED.name(),
				IsolationLevel.READ_COMMITTED.name());
		assertThat(original).extracting("id").containsExactly(IsolationLevel.READ_UNCOMMITTED.id(),
				IsolationLevel.READ_COMMITTED.id());
		assertThat(original).hasSize(IsolationLevel.values().length);
	}

}
