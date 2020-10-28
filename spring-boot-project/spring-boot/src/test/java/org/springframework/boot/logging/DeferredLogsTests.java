package org.springframework.boot.logging;

import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests for {@link DeferredLogs}.
 *

 */
class DeferredLogsTests {

	@Test
	void switchOverAllSwitchesLoggersWithOrderedOutput() {
		Log log1 = mock(Log.class);
		Log log2 = mock(Log.class);
		DeferredLogs loggers = new DeferredLogs();
		Log dlog1 = loggers.getLog(log1);
		Log dlog2 = loggers.getLog(log2);
		dlog1.info("a");
		dlog2.info("b");
		dlog1.info("c");
		dlog2.info("d");
		verifyNoInteractions(log1, log2);
		loggers.switchOverAll();
		InOrder ordered = inOrder(log1, log2);
		ordered.verify(log1).info("a", null);
		ordered.verify(log2).info("b", null);
		ordered.verify(log1).info("c", null);
		ordered.verify(log2).info("d", null);
		verifyNoMoreInteractions(log1, log2);
		dlog1.info("e");
		dlog2.info("f");
		ordered.verify(log1).info("e", null);
		ordered.verify(log2).info("f", null);
	}

}
