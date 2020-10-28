package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.CoreConstants;

/**
 * {@link ExtendedThrowableProxyConverter} that adds some additional whitespace around the
 * stack trace.
 *

 * @since 1.3.0
 */
public class ExtendedWhitespaceThrowableProxyConverter extends ExtendedThrowableProxyConverter {

	@Override
	protected String throwableProxyToString(IThrowableProxy tp) {
		return CoreConstants.LINE_SEPARATOR + super.throwableProxyToString(tp) + CoreConstants.LINE_SEPARATOR;
	}

}
