package org.springframework.boot;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.boot.system.ApplicationPid;
import org.springframework.util.StopWatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link StartupInfoLogger}.
 *


 */
class StartupInfoLoggerTests {

	private final Log log = mock(Log.class);

	@Test
	void startingFormat() throws UnknownHostException {
		given(this.log.isInfoEnabled()).willReturn(true);
		new StartupInfoLogger(getClass()).logStarting(this.log);
		ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
		verify(this.log).info(captor.capture());
		assertThat(captor.getValue().toString()).contains("Starting " + getClass().getSimpleName() + " using Java "
				+ System.getProperty("java.version") + " on " + InetAddress.getLocalHost().getHostName() + " with PID "
				+ new ApplicationPid() + " (started by " + System.getProperty("user.name") + " in "
				+ System.getProperty("user.dir") + ")");
	}

	@Test
	void startedFormat() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		given(this.log.isInfoEnabled()).willReturn(true);
		stopWatch.stop();
		new StartupInfoLogger(getClass()).logStarted(this.log, stopWatch);
		ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
		verify(this.log).info(captor.capture());
		assertThat(captor.getValue().toString()).matches("Started " + getClass().getSimpleName()
				+ " in \\d+\\.\\d{1,3} seconds \\(JVM running for \\d+\\.\\d{1,3}\\)");
	}

}
