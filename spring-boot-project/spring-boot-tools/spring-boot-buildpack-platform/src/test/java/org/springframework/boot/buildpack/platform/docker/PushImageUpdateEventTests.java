package org.springframework.boot.buildpack.platform.docker;

import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.docker.ProgressUpdateEvent.ProgressDetail;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PushImageUpdateEvent}.
 *

 */
class PushImageUpdateEventTests extends ProgressUpdateEventTests<PushImageUpdateEvent> {

	@Test
	void getIdReturnsId() {
		PushImageUpdateEvent event = createEvent();
		assertThat(event.getId()).isEqualTo("id");
	}

	@Test
	void getErrorReturnsErrorDetail() {
		PushImageUpdateEvent event = new PushImageUpdateEvent(null, null, null, null,
				new PushImageUpdateEvent.ErrorDetail("test message"));
		assertThat(event.getErrorDetail().getMessage()).isEqualTo("test message");
	}

	@Override
	protected PushImageUpdateEvent createEvent(String status, ProgressDetail progressDetail, String progress) {
		return new PushImageUpdateEvent("id", status, progressDetail, progress, null);
	}

}
