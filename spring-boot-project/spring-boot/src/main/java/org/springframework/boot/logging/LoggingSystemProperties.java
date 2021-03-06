package org.springframework.boot.logging;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.system.ApplicationPid;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.util.Assert;

/**
 * Utility to set system properties that can later be used by log configuration files.
 *






 * @since 2.0.0
 */
public class LoggingSystemProperties {

	/**
	 * The name of the System property that contains the process ID.
	 */
	public static final String PID_KEY = "PID";

	/**
	 * The name of the System property that contains the exception conversion word.
	 */
	public static final String EXCEPTION_CONVERSION_WORD = "LOG_EXCEPTION_CONVERSION_WORD";

	/**
	 * The name of the System property that contains the log file.
	 */
	public static final String LOG_FILE = "LOG_FILE";

	/**
	 * The name of the System property that contains the log path.
	 */
	public static final String LOG_PATH = "LOG_PATH";

	/**
	 * The name of the System property that contains the console log pattern.
	 */
	public static final String CONSOLE_LOG_PATTERN = "CONSOLE_LOG_PATTERN";

	/**
	 * The name of the System property that contains the console log charset.
	 */
	public static final String CONSOLE_LOG_CHARSET = "CONSOLE_LOG_CHARSET";

	/**
	 * The name of the System property that contains the file log pattern.
	 */
	public static final String FILE_LOG_PATTERN = "FILE_LOG_PATTERN";

	/**
	 * The name of the System property that contains the file log charset.
	 */
	public static final String FILE_LOG_CHARSET = "FILE_LOG_CHARSET";

	/**
	 * The name of the System property that contains the rolled-over log file name
	 * pattern.
	 * @deprecated since 2.4.0 in favor of
	 * {@link org.springframework.boot.logging.logback.LogbackLoggingSystemProperties#ROLLINGPOLICY_FILE_NAME_PATTERN}
	 */
	@Deprecated
	public static final String ROLLING_FILE_NAME_PATTERN = "ROLLING_FILE_NAME_PATTERN";

	/**
	 * The name of the System property that contains the clean history on start flag.
	 * @deprecated since 2.4.0 in favor of
	 * {@link org.springframework.boot.logging.logback.LogbackLoggingSystemProperties#ROLLINGPOLICY_CLEAN_HISTORY_ON_START}
	 */
	@Deprecated
	public static final String FILE_CLEAN_HISTORY_ON_START = "LOG_FILE_CLEAN_HISTORY_ON_START";

	/**
	 * The name of the System property that contains the file log max size.
	 * @deprecated since 2.4.0 in favor of
	 * {@link org.springframework.boot.logging.logback.LogbackLoggingSystemProperties#ROLLINGPOLICY_MAX_FILE_SIZE}
	 */
	@Deprecated
	public static final String FILE_MAX_SIZE = "LOG_FILE_MAX_SIZE";

	/**
	 * The name of the System property that contains the file total size cap.
	 * @deprecated since 2.4.0 in favor of
	 * {@link org.springframework.boot.logging.logback.LogbackLoggingSystemProperties#ROLLINGPOLICY_TOTAL_SIZE_CAP}
	 */
	@Deprecated
	public static final String FILE_TOTAL_SIZE_CAP = "LOG_FILE_TOTAL_SIZE_CAP";

	/**
	 * The name of the System property that contains the file log max history.
	 * @deprecated since 2.4.0 in favor of
	 * {@link org.springframework.boot.logging.logback.LogbackLoggingSystemProperties#ROLLINGPOLICY_MAX_HISTORY}
	 */
	@Deprecated
	public static final String FILE_MAX_HISTORY = "LOG_FILE_MAX_HISTORY";

	/**
	 * The name of the System property that contains the log level pattern.
	 */
	public static final String LOG_LEVEL_PATTERN = "LOG_LEVEL_PATTERN";

	/**
	 * The name of the System property that contains the log date-format pattern.
	 */
	public static final String LOG_DATEFORMAT_PATTERN = "LOG_DATEFORMAT_PATTERN";

	private final Environment environment;

	/**
	 * Create a new {@link LoggingSystemProperties} instance.
	 * @param environment the source environment
	 */
	public LoggingSystemProperties(Environment environment) {
		Assert.notNull(environment, "Environment must not be null");
		this.environment = environment;
	}

	protected Charset getDefaultCharset() {
		return StandardCharsets.UTF_8;
	}

	public final void apply() {
		apply(null);
	}

	public final void apply(LogFile logFile) {
		PropertyResolver resolver = getPropertyResolver();
		apply(logFile, resolver);
	}

	protected void apply(LogFile logFile, PropertyResolver resolver) {
		setSystemProperty(resolver, EXCEPTION_CONVERSION_WORD, "logging.exception-conversion-word");
		setSystemProperty(PID_KEY, new ApplicationPid().toString());
		setSystemProperty(resolver, CONSOLE_LOG_PATTERN, "logging.pattern.console");
		setSystemProperty(resolver, CONSOLE_LOG_CHARSET, "logging.charset.console", getDefaultCharset().name());
		setSystemProperty(resolver, LOG_DATEFORMAT_PATTERN, "logging.pattern.dateformat");
		setSystemProperty(resolver, FILE_LOG_PATTERN, "logging.pattern.file");
		setSystemProperty(resolver, FILE_LOG_CHARSET, "logging.charset.file", getDefaultCharset().name());
		setSystemProperty(resolver, LOG_LEVEL_PATTERN, "logging.pattern.level");
		applyDeprecated(resolver);
		if (logFile != null) {
			logFile.applyToSystemProperties();
		}
	}

	private void applyDeprecated(PropertyResolver resolver) {
		setSystemProperty(resolver, FILE_CLEAN_HISTORY_ON_START, "logging.file.clean-history-on-start");
		setSystemProperty(resolver, FILE_MAX_HISTORY, "logging.file.max-history");
		setSystemProperty(resolver, FILE_MAX_SIZE, "logging.file.max-size");
		setSystemProperty(resolver, FILE_TOTAL_SIZE_CAP, "logging.file.total-size-cap");
		setSystemProperty(resolver, ROLLING_FILE_NAME_PATTERN, "logging.pattern.rolling-file-name");
	}

	private PropertyResolver getPropertyResolver() {
		if (this.environment instanceof ConfigurableEnvironment) {
			PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(
					((ConfigurableEnvironment) this.environment).getPropertySources());
			resolver.setConversionService(((ConfigurableEnvironment) this.environment).getConversionService());
			resolver.setIgnoreUnresolvableNestedPlaceholders(true);
			return resolver;
		}
		return this.environment;
	}

	protected final void setSystemProperty(PropertyResolver resolver, String systemPropertyName, String propertyName) {
		setSystemProperty(resolver, systemPropertyName, propertyName, null);
	}

	protected final void setSystemProperty(PropertyResolver resolver, String systemPropertyName, String propertyName,
			String defaultValue) {
		String value = resolver.getProperty(propertyName);
		value = (value != null) ? value : defaultValue;
		setSystemProperty(systemPropertyName, value);
	}

	protected final void setSystemProperty(String name, String value) {
		if (System.getProperty(name) == null && value != null) {
			System.setProperty(name, value);
		}
	}

}
