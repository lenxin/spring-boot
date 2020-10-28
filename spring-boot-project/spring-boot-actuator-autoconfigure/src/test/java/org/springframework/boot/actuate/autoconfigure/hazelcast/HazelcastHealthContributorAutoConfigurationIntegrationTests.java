package org.springframework.boot.actuate.autoconfigure.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.hazelcast.HazelcastHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link HazelcastHealthContributorAutoConfiguration}.
 *

 */
class HazelcastHealthContributorAutoConfigurationIntegrationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(HazelcastHealthContributorAutoConfiguration.class,
					HazelcastAutoConfiguration.class, HealthContributorAutoConfiguration.class));

	@Test
	void hazelcastUp() {
		this.contextRunner.run((context) -> {
			assertThat(context).hasSingleBean(HazelcastInstance.class).hasSingleBean(HazelcastHealthIndicator.class);
			HazelcastInstance hazelcast = context.getBean(HazelcastInstance.class);
			Health health = context.getBean(HazelcastHealthIndicator.class).health();
			assertThat(health.getStatus()).isEqualTo(Status.UP);
			assertThat(health.getDetails()).containsOnlyKeys("name", "uuid").containsEntry("name", hazelcast.getName())
					.containsEntry("uuid", hazelcast.getLocalEndpoint().getUuid().toString());
		});
	}

	@Test
	void hazelcastDown() {
		this.contextRunner.run((context) -> {
			context.getBean(HazelcastInstance.class).shutdown();
			assertThat(context).hasSingleBean(HazelcastHealthIndicator.class);
			Health health = context.getBean(HazelcastHealthIndicator.class).health();
			assertThat(health.getStatus()).isEqualTo(Status.DOWN);
		});
	}

}
