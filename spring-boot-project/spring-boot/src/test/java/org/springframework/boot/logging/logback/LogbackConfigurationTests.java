package org.springframework.boot.logging.logback;

import java.io.File;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for default Logback configuration provided by {@code base.xml}.
 *


 */
class LogbackConfigurationTests {

	@Test
	void consolePatternCanBeOverridden() throws JoranException {
		JoranConfigurator configurator = new JoranConfigurator();
		LoggerContext context = new LoggerContext();
		configurator.setContext(context);
		configurator.doConfigure(new File("src/test/resources/custom-console-log-pattern.xml"));
		Appender<ILoggingEvent> appender = context.getLogger("ROOT").getAppender("CONSOLE");
		assertThat(appender).isInstanceOf(ConsoleAppender.class);
		Encoder<?> encoder = ((ConsoleAppender<?>) appender).getEncoder();
		assertThat(encoder).isInstanceOf(PatternLayoutEncoder.class);
		assertThat(((PatternLayoutEncoder) encoder).getPattern()).isEqualTo("foo");
	}

	@Test
	void filePatternCanBeOverridden() throws JoranException {
		JoranConfigurator configurator = new JoranConfigurator();
		LoggerContext context = new LoggerContext();
		configurator.setContext(context);
		configurator.doConfigure(new File("src/test/resources/custom-file-log-pattern.xml"));
		Appender<ILoggingEvent> appender = context.getLogger("ROOT").getAppender("FILE");
		assertThat(appender).isInstanceOf(FileAppender.class);
		Encoder<?> encoder = ((FileAppender<?>) appender).getEncoder();
		assertThat(encoder).isInstanceOf(PatternLayoutEncoder.class);
		assertThat(((PatternLayoutEncoder) encoder).getPattern()).isEqualTo("bar");
	}

	@Test
	void defaultRollingFileNamePattern() throws JoranException {
		JoranConfigurator configurator = new JoranConfigurator();
		LoggerContext context = new LoggerContext();
		configurator.setContext(context);
		configurator.doConfigure(new File("src/test/resources/custom-file-log-pattern.xml"));
		Appender<ILoggingEvent> appender = context.getLogger("ROOT").getAppender("FILE");
		assertThat(appender).isInstanceOf(RollingFileAppender.class);
		RollingPolicy rollingPolicy = ((RollingFileAppender<?>) appender).getRollingPolicy();
		String fileNamePattern = ((SizeAndTimeBasedRollingPolicy<?>) rollingPolicy).getFileNamePattern();
		assertThat(fileNamePattern).endsWith("spring.log.%d{yyyy-MM-dd}.%i.gz");
	}

	@Test
	void customRollingFileNamePattern() throws JoranException {
		JoranConfigurator configurator = new JoranConfigurator();
		LoggerContext context = new LoggerContext();
		configurator.setContext(context);
		configurator.doConfigure(new File("src/test/resources/custom-file-log-pattern-with-fileNamePattern.xml"));
		Appender<ILoggingEvent> appender = context.getLogger("ROOT").getAppender("FILE");
		assertThat(appender).isInstanceOf(RollingFileAppender.class);
		RollingPolicy rollingPolicy = ((RollingFileAppender<?>) appender).getRollingPolicy();
		String fileNamePattern = ((SizeAndTimeBasedRollingPolicy<?>) rollingPolicy).getFileNamePattern();
		assertThat(fileNamePattern).endsWith("my.log.%d{yyyyMMdd}.%i.gz");
	}

}
