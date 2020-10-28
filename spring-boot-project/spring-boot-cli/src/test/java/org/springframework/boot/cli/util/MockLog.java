package org.springframework.boot.cli.util;

import static org.mockito.Mockito.mock;

/**
 * Mock log for testing message output.
 *

 */
public interface MockLog extends LogListener {

	static MockLog attach() {
		MockLog log = mock(MockLog.class);
		Log.setListener(log);
		return log;
	}

	static void clear() {
		Log.setListener(null);
	}

}
