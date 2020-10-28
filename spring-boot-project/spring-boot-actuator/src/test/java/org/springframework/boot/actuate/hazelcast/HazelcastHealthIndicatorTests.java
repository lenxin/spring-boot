package org.springframework.boot.actuate.hazelcast;

import java.io.IOException;

import com.hazelcast.core.HazelcastException;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastInstanceFactory;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link HazelcastHealthIndicator}.
 *


 */
class HazelcastHealthIndicatorTests {

	@Test
	void hazelcastUp() throws IOException {
		HazelcastInstance hazelcast = new HazelcastInstanceFactory(new ClassPathResource("hazelcast.xml"))
				.getHazelcastInstance();
		try {
			Health health = new HazelcastHealthIndicator(hazelcast).health();
			assertThat(health.getStatus()).isEqualTo(Status.UP);
			assertThat(health.getDetails()).containsOnlyKeys("name", "uuid").containsEntry("name",
					"actuator-hazelcast");
			assertThat(health.getDetails().get("uuid")).asString().isNotEmpty();
		}
		finally {
			hazelcast.shutdown();
		}
	}

	@Test
	void hazelcastDown() {
		HazelcastInstance hazelcast = mock(HazelcastInstance.class);
		given(hazelcast.executeTransaction(any())).willThrow(new HazelcastException());
		Health health = new HazelcastHealthIndicator(hazelcast).health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
	}

}
