package org.springframework.boot.actuate.system;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.util.unit.DataSize;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link DiskSpaceHealthIndicator}.
 *


 */
@ExtendWith(MockitoExtension.class)
class DiskSpaceHealthIndicatorTests {

	private static final DataSize THRESHOLD = DataSize.ofKilobytes(1);

	private static final DataSize TOTAL_SPACE = DataSize.ofKilobytes(10);

	@Mock
	private File fileMock;

	private HealthIndicator healthIndicator;

	@BeforeEach
	void setUp() {
		this.healthIndicator = new DiskSpaceHealthIndicator(this.fileMock, THRESHOLD);
	}

	@Test
	void diskSpaceIsUp() {
		given(this.fileMock.exists()).willReturn(true);
		long freeSpace = THRESHOLD.toBytes() + 10;
		given(this.fileMock.getUsableSpace()).willReturn(freeSpace);
		given(this.fileMock.getTotalSpace()).willReturn(TOTAL_SPACE.toBytes());
		Health health = this.healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails().get("threshold")).isEqualTo(THRESHOLD.toBytes());
		assertThat(health.getDetails().get("free")).isEqualTo(freeSpace);
		assertThat(health.getDetails().get("total")).isEqualTo(TOTAL_SPACE.toBytes());
		assertThat(health.getDetails().get("exists")).isEqualTo(true);
	}

	@Test
	void diskSpaceIsDown() {
		given(this.fileMock.exists()).willReturn(true);
		long freeSpace = THRESHOLD.toBytes() - 10;
		given(this.fileMock.getUsableSpace()).willReturn(freeSpace);
		given(this.fileMock.getTotalSpace()).willReturn(TOTAL_SPACE.toBytes());
		Health health = this.healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
		assertThat(health.getDetails().get("threshold")).isEqualTo(THRESHOLD.toBytes());
		assertThat(health.getDetails().get("free")).isEqualTo(freeSpace);
		assertThat(health.getDetails().get("total")).isEqualTo(TOTAL_SPACE.toBytes());
		assertThat(health.getDetails().get("exists")).isEqualTo(true);
	}

	@Test
	void whenPathDoesNotExistDiskSpaceIsDown() {
		Health health = new DiskSpaceHealthIndicator(new File("does/not/exist"), THRESHOLD).health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
		assertThat(health.getDetails().get("free")).isEqualTo(0L);
		assertThat(health.getDetails().get("total")).isEqualTo(0L);
		assertThat(health.getDetails().get("exists")).isEqualTo(false);
	}

}
