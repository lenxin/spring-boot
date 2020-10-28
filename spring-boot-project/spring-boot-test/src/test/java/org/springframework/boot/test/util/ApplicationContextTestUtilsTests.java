package org.springframework.boot.test.util;

import org.junit.jupiter.api.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ApplicationContextTestUtils}.
 *

 */
class ApplicationContextTestUtilsTests {

	@Test
	void closeNull() {
		ApplicationContextTestUtils.closeAll(null);
	}

	@Test
	void closeNonClosableContext() {
		ApplicationContext mock = mock(ApplicationContext.class);
		ApplicationContextTestUtils.closeAll(mock);
	}

	@Test
	void closeContextAndParent() {
		ConfigurableApplicationContext mock = mock(ConfigurableApplicationContext.class);
		ConfigurableApplicationContext parent = mock(ConfigurableApplicationContext.class);
		given(mock.getParent()).willReturn(parent);
		given(parent.getParent()).willReturn(null);
		ApplicationContextTestUtils.closeAll(mock);
		verify(mock).getParent();
		verify(mock).close();
		verify(parent).getParent();
		verify(parent).close();
	}

}
