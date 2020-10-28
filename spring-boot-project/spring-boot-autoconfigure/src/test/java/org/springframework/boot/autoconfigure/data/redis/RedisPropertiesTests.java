package org.springframework.boot.autoconfigure.data.redis;

import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RedisProperties}.
 *

 */
class RedisPropertiesTests {

	@Test
	void lettuceDefaultsAreConsistent() {
		Lettuce lettuce = new RedisProperties().getLettuce();
		ClusterTopologyRefreshOptions defaultClusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
				.build();
		assertThat(lettuce.getCluster().getRefresh().isDynamicRefreshSources())
				.isEqualTo(defaultClusterTopologyRefreshOptions.useDynamicRefreshSources());
	}

}
