package org.springframework.boot.logging.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.core.pattern.ThrowablePatternConverter;

/**
 * {@link ThrowablePatternConverter} that adds some additional whitespace around the stack
 * trace.
 *

 * @since 1.3.0
 */
@Plugin(name = "WhitespaceThrowablePatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "wEx", "wThrowable", "wException" })
public final class WhitespaceThrowablePatternConverter extends ThrowablePatternConverter {

	private WhitespaceThrowablePatternConverter(Configuration configuration, String[] options) {
		super("WhitespaceThrowable", "throwable", options, configuration);
	}

	@Override
	public void format(LogEvent event, StringBuilder buffer) {
		if (event.getThrown() != null) {
			buffer.append(this.options.getSeparator());
			super.format(event, buffer);
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
	public static WhitespaceThrowablePatternConverter newInstance(Configuration configuration, String[] options) {
		return new WhitespaceThrowablePatternConverter(configuration, options);
	}

}
