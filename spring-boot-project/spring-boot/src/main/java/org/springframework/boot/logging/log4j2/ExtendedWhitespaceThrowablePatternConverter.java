package org.springframework.boot.logging.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.ExtendedThrowablePatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.core.pattern.ThrowablePatternConverter;

/**
 * {@link ThrowablePatternConverter} that adds some additional whitespace around the stack
 * trace.
 *


 * @since 1.3.0
 */
@Plugin(name = "ExtendedWhitespaceThrowablePatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "xwEx", "xwThrowable", "xwException" })
public final class ExtendedWhitespaceThrowablePatternConverter extends ThrowablePatternConverter {

	private final ExtendedThrowablePatternConverter delegate;

	private ExtendedWhitespaceThrowablePatternConverter(Configuration configuration, String[] options) {
		super("WhitespaceExtendedThrowable", "throwable", options, configuration);
		this.delegate = ExtendedThrowablePatternConverter.newInstance(configuration, options);
	}

	@Override
	public void format(LogEvent event, StringBuilder buffer) {
		if (event.getThrown() != null) {
			buffer.append(this.options.getSeparator());
			this.delegate.format(event, buffer);
			buffer.append(this.options.getSeparator());
		}
	}

	/**
	 * Creates a new instance of the class. Required by Log4J2.
	 * @param configuration current configuration
	 * @param options pattern options, may be null. If first element is "short", only the
	 * first line of the throwable will be formatted.
	 * @return a new {@code WhitespaceThrowablePatternConverter}
	 */
	public static ExtendedWhitespaceThrowablePatternConverter newInstance(Configuration configuration,
			String[] options) {
		return new ExtendedWhitespaceThrowablePatternConverter(configuration, options);
	}

}
