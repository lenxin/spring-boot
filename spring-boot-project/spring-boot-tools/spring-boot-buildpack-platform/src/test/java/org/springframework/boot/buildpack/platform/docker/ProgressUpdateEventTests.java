package org.springframework.boot.buildpack.platform.docker;

import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.docker.ProgressUpdateEvent.ProgressDetail;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ProgressUpdateEvent}.
 *


 */
abstract class ProgressUpdateEventTests<E extends ProgressUpdateEvent> {

	@Test
	void getStatusReturnsStatus() {
		ProgressUpdateEvent event = createEvent();
		assertThat(event.getStatus()).isEqualTo("status");
	}

	@Test
	void getProgressDetailsReturnsProgressDetails() {
		ProgressUpdateEvent event = createEvent();
		assertThat(event.getProgressDetail().getCurrent()).isEqualTo(1);
		assertThat(event.getProgressDetail().getTotal()).isEqualTo(2);
	}

	@Test
	void getProgressReturnsProgress() {
		ProgressUpdateEvent event = createEvent();
		assertThat(event.getProgress()).isEqualTo("progress");
	}

	@Test
	void progressDetailIsEmptyWhenCurrentIsNullReturnsTrue() {
		ProgressDetail detail = new ProgressDetail(null, 2);
		assertThat(ProgressDetail.isEmpty(detail)).isTrue();
	}

	@Test
	void progressDetailIsEmptyWhenTotalIsNullReturnsTrue() {
		ProgressDetail detail = new ProgressDetail(1, null);
		assertThat(ProgressDetail.isEmpty(detail)).isTrue();
	}

	@Test
	void progressDetailIsEmptyWhenTotalAndCurrentAreNotNullReturnsFalse() {
		ProgressDetail detail = new ProgressDetail(1, 2);
		assertThat(ProgressDetail.isEmpty(detail)).isFalse();
	}

	protected E createEvent() {
		return createEvent("status", new ProgressDetail(1, 2), "progress");
	}

	protected abstract E createEvent(String status, ProgressDetail progressDetail, String progress);

}
