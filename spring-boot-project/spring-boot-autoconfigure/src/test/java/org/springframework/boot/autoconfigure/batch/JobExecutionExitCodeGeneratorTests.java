package org.springframework.boot.autoconfigure.batch;

import org.junit.jupiter.api.Test;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JobExecutionExitCodeGenerator}.
 *

 */
class JobExecutionExitCodeGeneratorTests {

	private final JobExecutionExitCodeGenerator generator = new JobExecutionExitCodeGenerator();

	@Test
	void testExitCodeForRunning() {
		this.generator.onApplicationEvent(new JobExecutionEvent(new JobExecution(0L)));
		assertThat(this.generator.getExitCode()).isEqualTo(1);
	}

	@Test
	void testExitCodeForCompleted() {
		JobExecution execution = new JobExecution(0L);
		execution.setStatus(BatchStatus.COMPLETED);
		this.generator.onApplicationEvent(new JobExecutionEvent(execution));
		assertThat(this.generator.getExitCode()).isEqualTo(0);
	}

	@Test
	void testExitCodeForFailed() {
		JobExecution execution = new JobExecution(0L);
		execution.setStatus(BatchStatus.FAILED);
		this.generator.onApplicationEvent(new JobExecutionEvent(execution));
		assertThat(this.generator.getExitCode()).isEqualTo(5);
	}

}
