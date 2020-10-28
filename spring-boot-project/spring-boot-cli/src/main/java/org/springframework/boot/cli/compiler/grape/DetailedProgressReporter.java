package org.springframework.boot.cli.compiler.grape;

import java.io.PrintStream;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transfer.TransferResource;

/**
 * Provide detailed progress feedback for long running resolves.
 *

 */
final class DetailedProgressReporter implements ProgressReporter {

	DetailedProgressReporter(DefaultRepositorySystemSession session, final PrintStream out) {

		session.setTransferListener(new AbstractTransferListener() {

			@Override
			public void transferStarted(TransferEvent event) throws TransferCancelledException {
				out.println("Downloading: " + getResourceIdentifier(event.getResource()));
			}

			@Override
			public void transferSucceeded(TransferEvent event) {
				out.printf("Downloaded: %s (%s)%n", getResourceIdentifier(event.getResource()),
						getTransferSpeed(event));
			}
		});
	}

	private String getResourceIdentifier(TransferResource resource) {
		return resource.getRepositoryUrl() + resource.getResourceName();
	}

	private String getTransferSpeed(TransferEvent event) {
		long kb = event.getTransferredBytes() / 1024;
		float seconds = (System.currentTimeMillis() - event.getResource().getTransferStartTime()) / 1000.0f;

		return String.format("%dKB at %.1fKB/sec", kb, (kb / seconds));
	}

	@Override
	public void finished() {
	}

}
