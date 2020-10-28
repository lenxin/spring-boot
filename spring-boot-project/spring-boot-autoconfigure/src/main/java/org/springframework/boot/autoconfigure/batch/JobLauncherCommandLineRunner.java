package org.springframework.boot.autoconfigure.batch;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.ApplicationRunner;

/**
 * {@link ApplicationRunner} to {@link JobLauncher launch} Spring Batch jobs. Runs all
 * jobs in the surrounding context by default. Can also be used to launch a specific job
 * by providing a jobName.
 *



 * @since 1.0.0
 * @deprecated since 2.3.0 in favor of {@link JobLauncherApplicationRunner}
 */
@Deprecated
public class JobLauncherCommandLineRunner extends JobLauncherApplicationRunner {

	/**
	 * Create a new {@link JobLauncherCommandLineRunner}.
	 * @param jobLauncher to launch jobs
	 * @param jobExplorer to check the job repository for previous executions
	 * @param jobRepository to check if a job instance exists with the given parameters
	 * when running a job
	 */
	public JobLauncherCommandLineRunner(JobLauncher jobLauncher, JobExplorer jobExplorer, JobRepository jobRepository) {
		super(jobLauncher, jobExplorer, jobRepository);
	}

}
