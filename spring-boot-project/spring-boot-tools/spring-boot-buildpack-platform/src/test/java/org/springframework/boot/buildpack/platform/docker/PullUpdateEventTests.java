package org.springframework.boot.buildpack.platform.docker;

import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.json.AbstractJsonTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PullImageUpdateEvent}.
 *

 */
class PullUpdateEventTests extends AbstractJsonTests {

	@Test
	void readValueWhenFullDeserializesJson() throws Exception {
		PullImageUpdateEvent event = getObjectMapper().readValue(getContent("pull-update-full.json"),
				PullImageUpdateEvent.class);
		assertThat(event.getId()).isEqualTo("4f4fb700ef54");
		assertThat(event.getStatus()).isEqualTo("Extracting");
		assertThat(event.getProgressDetail().getCurrent()).isEqualTo(16);
		assertThat(event.getProgressDetail().getTotal()).isEqualTo(32);
		assertThat(event.getProgress()).isEqualTo("[==================================================>]      32B/32B");
	}

	@Test
	void readValueWhenMinimalDeserializesJson() throws Exception {
		PullImageUpdateEvent event = getObjectMapper().readValue(getContent("pull-update-minimal.json"),
				PullImageUpdateEvent.class);
		assertThat(event.getId()).isNull();
		assertThat(event.getStatus()).isEqualTo("Status: Downloaded newer image for paketo-buildpacks/cnb:base");
		assertThat(event.getProgressDetail()).isNull();
		assertThat(event.getProgress()).isNull();
	}

	@Test
	void readValueWhenEmptyDetailsDeserializesJson() throws Exception {
		PullImageUpdateEvent event = getObjectMapper().readValue(getContent("pull-with-empty-details.json"),
				PullImageUpdateEvent.class);
		assertThat(event.getId()).isEqualTo("d837a2a1365e");
		assertThat(event.getStatus()).isEqualTo("Pulling fs layer");
		assertThat(event.getProgressDetail()).isNull();
		assertThat(event.getProgress()).isNull();
	}

}
