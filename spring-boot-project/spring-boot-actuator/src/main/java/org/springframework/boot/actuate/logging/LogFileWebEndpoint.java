package org.springframework.boot.actuate.logging;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.boot.logging.LogFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * Web {@link Endpoint @Endpoint} that provides access to an application's log file.
 *



 * @since 2.0.0
 */
@WebEndpoint(id = "logfile")
public class LogFileWebEndpoint {

	private static final Log logger = LogFactory.getLog(LogFileWebEndpoint.class);

	private File externalFile;

	private final LogFile logFile;

	public LogFileWebEndpoint(LogFile logFile, File externalFile) {
		this.externalFile = externalFile;
		this.logFile = logFile;
	}

	@ReadOperation(produces = "text/plain; charset=UTF-8")
	public Resource logFile() {
		Resource logFileResource = getLogFileResource();
		if (logFileResource == null || !logFileResource.isReadable()) {
			return null;
		}
		return logFileResource;
	}

	private Resource getLogFileResource() {
		if (this.externalFile != null) {
			return new FileSystemResource(this.externalFile);
		}
		if (this.logFile == null) {
			logger.debug("Missing 'logging.file.name' or 'logging.file.path' properties");
			return null;
		}
		return new FileSystemResource(this.logFile.toString());
	}

}
