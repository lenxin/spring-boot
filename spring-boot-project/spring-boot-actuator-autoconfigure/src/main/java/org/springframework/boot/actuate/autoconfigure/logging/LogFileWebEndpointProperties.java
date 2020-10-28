package org.springframework.boot.actuate.autoconfigure.logging;

import java.io.File;

import org.springframework.boot.actuate.logging.LogFileWebEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for {@link LogFileWebEndpoint}.
 *

 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "management.endpoint.logfile")
public class LogFileWebEndpointProperties {

	/**
	 * External Logfile to be accessed. Can be used if the logfile is written by output
	 * redirect and not by the logging system itself.
	 */
	private File externalFile;

	public File getExternalFile() {
		return this.externalFile;
	}

	public void setExternalFile(File externalFile) {
		this.externalFile = externalFile;
	}

}
