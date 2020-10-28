package org.springframework.boot.web.server;

import org.springframework.util.unit.DataSize;

/**
 * Simple server-independent abstraction for compression configuration.
 *



 * @since 2.0.0
 */
public class Compression {

	private boolean enabled = false;

	private String[] mimeTypes = new String[] { "text/html", "text/xml", "text/plain", "text/css", "text/javascript",
			"application/javascript", "application/json", "application/xml" };

	private String[] excludedUserAgents = null;

	private DataSize minResponseSize = DataSize.ofKilobytes(2);

	/**
	 * Return whether response compression is enabled.
	 * @return {@code true} if response compression is enabled
	 */
	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Return the MIME types that should be compressed.
	 * @return the MIME types that should be compressed
	 */
	public String[] getMimeTypes() {
		return this.mimeTypes;
	}

	public void setMimeTypes(String[] mimeTypes) {
		this.mimeTypes = mimeTypes;
	}

	public String[] getExcludedUserAgents() {
		return this.excludedUserAgents;
	}

	public void setExcludedUserAgents(String[] excludedUserAgents) {
		this.excludedUserAgents = excludedUserAgents;
	}

	/**
	 * Return the minimum "Content-Length" value that is required for compression to be
	 * performed.
	 * @return the minimum content size in bytes that is required for compression
	 */
	public DataSize getMinResponseSize() {
		return this.minResponseSize;
	}

	public void setMinResponseSize(DataSize minSize) {
		this.minResponseSize = minSize;
	}

}
