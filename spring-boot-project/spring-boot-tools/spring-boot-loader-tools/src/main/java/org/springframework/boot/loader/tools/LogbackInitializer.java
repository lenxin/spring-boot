package org.springframework.boot.loader.tools;

import ch.qos.logback.classic.Level;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.impl.StaticLoggerBinder;

import org.springframework.util.ClassUtils;

/**
 * Utility to initialize logback (when present) to use INFO level logging.
 *

 * @since 1.1.0
 */
public abstract class LogbackInitializer {

	public static void initialize() {
		if (ClassUtils.isPresent("org.slf4j.impl.StaticLoggerBinder", null)
				&& ClassUtils.isPresent("ch.qos.logback.classic.Logger", null)) {
			new Initializer().setRootLogLevel();
		}
	}

	private static class Initializer {

		void setRootLogLevel() {
			ILoggerFactory factory = StaticLoggerBinder.getSingleton().getLoggerFactory();
			Logger logger = factory.getLogger(Logger.ROOT_LOGGER_NAME);
			((ch.qos.logback.classic.Logger) logger).setLevel(Level.INFO);
		}

	}

}
