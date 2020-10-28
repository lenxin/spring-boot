package org.springframework.boot.autoconfigure.neo4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.Logger;
import org.neo4j.driver.Logging;

/**
 * Shim to use Spring JCL implementation, delegating all the hard work of deciding the
 * underlying system to Spring and Spring Boot.
 *

 */
class Neo4jSpringJclLogging implements Logging {

	/**
	 * This prefix gets added to the log names the driver requests to add some namespace
	 * around it in a bigger application scenario.
	 */
	private static final String AUTOMATIC_PREFIX = "org.neo4j.driver.";

	@Override
	public Logger getLog(String name) {
		String requestedLog = name;
		if (!requestedLog.startsWith(AUTOMATIC_PREFIX)) {
			requestedLog = AUTOMATIC_PREFIX + name;
		}
		Log springJclLog = LogFactory.getLog(requestedLog);
		return new SpringJclLogger(springJclLog);
	}

	private static final class SpringJclLogger implements Logger {

		private final Log delegate;

		SpringJclLogger(Log delegate) {
			this.delegate = delegate;
		}

		@Override
		public void error(String message, Throwable cause) {
			this.delegate.error(message, cause);
		}

		@Override
		public void info(String format, Object... params) {
			this.delegate.info(String.format(format, params));
		}

		@Override
		public void warn(String format, Object... params) {
			this.delegate.warn(String.format(format, params));
		}

		@Override
		public void warn(String message, Throwable cause) {
			this.delegate.warn(message, cause);
		}

		@Override
		public void debug(String format, Object... params) {
			if (isDebugEnabled()) {
				this.delegate.debug(String.format(format, params));
			}
		}

		@Override
		public void trace(String format, Object... params) {
			if (isTraceEnabled()) {
				this.delegate.trace(String.format(format, params));
			}
		}

		@Override
		public boolean isTraceEnabled() {
			return this.delegate.isTraceEnabled();
		}

		@Override
		public boolean isDebugEnabled() {
			return this.delegate.isDebugEnabled();
		}

	}

}
