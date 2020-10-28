package org.springframework.boot.buildpack.platform.docker;

import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.docker.ProgressUpdateEvent.ProgressDetail;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LoadImageUpdateEvent}.
 *


 */
class LoadImageUpdateEventTests extends ProgressUpdateEventTests<LoadImageUpdateEvent> {

	@Test
	void getStreamReturnsStream() {
		LoadImageUpdateEvent event = createEvent();
		assertThat(event.getStream()).isEqualTo("stream");
	}

	@Override
	protected LoadImageUpdateEvent createEvent(String status, ProgressDetail progressDetail, String progress) {
		return new LoadImageUpdateEvent("stream", status, progressDetail, progress);
	}

}
