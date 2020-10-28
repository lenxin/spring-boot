package org.springframework.boot;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Tests for {@link SpringBootExceptionHandler}.
 *


 */
class SpringBootExceptionHandlerTests {

	private final UncaughtExceptionHandler parent = mock(UncaughtExceptionHandler.class);

	private final SpringBootExceptionHandler handler = new SpringBootExceptionHandler(this.parent);

	@Test
	void uncaughtExceptionDoesNotForwardLoggedErrorToParent() {
		Thread thread = Thread.currentThread();
		Exception ex = new Exception();
		this.handler.registerLoggedException(ex);
		this.handler.uncaughtException(thread, ex);
		verifyNoInteractions(this.parent);
	}

	@Test
	void uncaughtExceptionForwardsLogConfigurationErrorToParent() {
		Thread thread = Thread.currentThread();
		Exception ex = new Exception("[stuff] Logback configuration error detected [stuff]");
		this.handler.registerLoggedException(ex);
		this.handler.uncaughtException(thread, ex);
		verify(this.parent).uncaughtException(thread, ex);
	}

	@Test
	void uncaughtExceptionForwardsWrappedLogConfigurationErrorToParent() {
		Thread thread = Thread.currentThread();
		Exception ex = new InvocationTargetException(
				new Exception("[stuff] Logback configuration error detected [stuff]", new Exception()));
		this.handler.registerLoggedException(ex);
		this.handler.uncaughtException(thread, ex);
		verify(this.parent).uncaughtException(thread, ex);
	}

}
