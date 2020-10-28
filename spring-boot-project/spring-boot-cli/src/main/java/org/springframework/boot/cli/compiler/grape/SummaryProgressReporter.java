package org.springframework.boot.cli.compiler.grape;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.eclipse.aether.AbstractRepositoryListener;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositoryEvent;
import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.TransferEvent;

/**
 * Provide high-level progress feedback for long running resolves.
 *


 */
final class SummaryProgressReporter implements ProgressReporter {

	private static final long INITIAL_DELAY = TimeUnit.SECONDS.toMillis(3);

	private static final long PROGRESS_DELAY = TimeUnit.SECONDS.toMillis(1);

	private final long startTime = System.currentTimeMillis();

	private final PrintStream out;

	private long lastProgressTime = System.currentTimeMillis();

	private boolean started;

	private boolean finished;

	SummaryProgressReporter(DefaultRepositorySystemSession session, PrintStream out) {
		this.out = out;
		session.setTransferListener(new AbstractTransferListener() {

			@Override
			public void transferStarted(TransferEvent event) {
				reportProgress();
			}

			@Override
			public void transferProgressed(TransferEvent event) {
				reportProgress();
			}

		});
		session.setRepositoryListener(new AbstractRepositoryListener() {

			@Override
			public void artifactResolved(RepositoryEvent event) {
				reportProgress();
			}

		});
	}

	private void reportProgress() {
		if (!this.finished && System.currentTimeMillis() - this.startTime > INITIAL_DELAY) {
			if (!this.started) {
				this.started = true;
				this.out.print("Resolving dependencies..");
				this.lastProgressTime = System.currentTimeMillis();
			}
			else if (System.currentTimeMillis() - this.lastProgressTime > PROGRESS_DELAY) {
				this.out.print(".");
				this.lastProgressTime = System.currentTimeMillis();
			}
		}
	}

	@Override
	public void finished() {
		if (this.started && !this.finished) {
			this.finished = true;
			this.out.println();
		}
	}

}
