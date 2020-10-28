package org.springframework.boot.buildpack.platform.docker;

import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.docker.ProgressUpdateEvent.ProgressDetail;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PullImageUpdateEvent}.
 *


 */
class PullImageUpdateEventTests extends ProgressUpdateEventTests<PullImageUpdateEvent> {

	@Test
	void getIdReturnsId() {
		PullImageUpdateEvent event = createEvent();
		assertThat(event.getId()).isEqualTo("id");
	}

	@Override
	protected PullImageUpdateEvent createEvent(String status, ProgressDetail progressDetail, String progress) {
		return new PullImageUpdateEvent("id", status, progressDetail, progress);
	}

}
