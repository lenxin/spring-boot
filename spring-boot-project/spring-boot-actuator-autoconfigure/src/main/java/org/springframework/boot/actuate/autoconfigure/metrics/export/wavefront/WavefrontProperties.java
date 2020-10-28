package org.springframework.boot.actuate.autoconfigure.metrics.export.wavefront;

import java.net.URI;
import java.time.Duration;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PushRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring Wavefront
 * metrics export.
 *


 * @since 2.0.0
 */
@ConfigurationProperties("management.metrics.export.wavefront")
public class WavefrontProperties extends PushRegistryProperties {

	/**
	 * URI to ship metrics to.
	 */
	private URI uri = URI.create("https://longboard.wavefront.com");

	/**
	 * Unique identifier for the app instance that is the source of metrics being
	 * published to Wavefront. Defaults to the local host name.
	 */
	private String source;

	/**
	 * API token used when publishing metrics directly to the Wavefront API host.
	 */
	private String apiToken;

	/**
	 * Global prefix to separate metrics originating from this app's white box
	 * instrumentation from those originating from other Wavefront integrations when
	 * viewed in the Wavefront UI.
	 */
	private String globalPrefix;

	private final Sender sender = new Sender();

	public URI getUri() {
		return this.uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getApiToken() {
		return this.apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public String getGlobalPrefix() {
		return this.globalPrefix;
	}

	public void setGlobalPrefix(String globalPrefix) {
		this.globalPrefix = globalPrefix;
	}

	public Sender getSender() {
		return this.sender;
	}

	public static class Sender {

		private int maxQueueSize = 50000;

		private Duration flushInterval = Duration.ofSeconds(1);

		private DataSize messageSize = DataSize.ofBytes(Integer.MAX_VALUE);

		public int getMaxQueueSize() {
			return this.maxQueueSize;
		}

		public void setMaxQueueSize(int maxQueueSize) {
			this.maxQueueSize = maxQueueSize;
		}

		public Duration getFlushInterval() {
			return this.flushInterval;
		}

		public void setFlushInterval(Duration flushInterval) {
			this.flushInterval = flushInterval;
		}

		public DataSize getMessageSize() {
			return this.messageSize;
		}

		public void setMessageSize(DataSize messageSize) {
			this.messageSize = messageSize;
		}

	}

}
