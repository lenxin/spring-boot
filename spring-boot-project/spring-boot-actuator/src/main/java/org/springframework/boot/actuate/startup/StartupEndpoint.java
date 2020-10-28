package org.springframework.boot.actuate.startup;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.context.metrics.buffering.StartupTimeline;

/**
 * {@link Endpoint @Endpoint} to expose the timeline of the
 * {@link org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup
 * application startup}.
 *

 * @since 2.4.0
 */
@Endpoint(id = "startup")
public class StartupEndpoint {

	private final BufferingApplicationStartup applicationStartup;

	/**
	 * Creates a new {@code StartupEndpoint} that will describe the timeline of buffered
	 * application startup events.
	 * @param applicationStartup the application startup
	 */
	public StartupEndpoint(BufferingApplicationStartup applicationStartup) {
		this.applicationStartup = applicationStartup;
	}

	@WriteOperation
	public StartupResponse startup() {
		StartupTimeline startupTimeline = this.applicationStartup.drainBufferedTimeline();
		return new StartupResponse(startupTimeline);
	}

	/**
	 * A description of an application startup, primarily intended for serialization to
	 * JSON.
	 */
	public static final class StartupResponse {

		private final String springBootVersion;

		private final StartupTimeline timeline;

		private StartupResponse(StartupTimeline timeline) {
			this.timeline = timeline;
			this.springBootVersion = SpringBootVersion.getVersion();
		}

		public String getSpringBootVersion() {
			return this.springBootVersion;
		}

		public StartupTimeline getTimeline() {
			return this.timeline;
		}

	}

}
