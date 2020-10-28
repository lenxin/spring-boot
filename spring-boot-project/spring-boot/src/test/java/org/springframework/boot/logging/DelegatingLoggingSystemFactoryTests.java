package org.springframework.boot.logging;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Tests for {@link DelegatingLoggingSystemFactory}.
 *

 */
class DelegatingLoggingSystemFactoryTests {

	private ClassLoader classLoader = getClass().getClassLoader();

	@Test
	void getLoggingSystemWhenDelegatesFunctionIsNullReturnsNull() {
		DelegatingLoggingSystemFactory factory = new DelegatingLoggingSystemFactory(null);
		assertThat(factory.getLoggingSystem(this.classLoader)).isNull();
	}

	@Test
	void getLoggingSystemWhenDelegatesFunctionReturnsNullReturnsNull() {
		DelegatingLoggingSystemFactory factory = new DelegatingLoggingSystemFactory((cl) -> null);
		assertThat(factory.getLoggingSystem(this.classLoader)).isNull();
	}

	@Test
	void getLoggingSystemReturnsFirstNonNullLoggingSystem() {
		List<LoggingSystemFactory> delegates = new ArrayList<>();
		delegates.add(mock(LoggingSystemFactory.class));
		delegates.add(mock(LoggingSystemFactory.class));
		delegates.add(mock(LoggingSystemFactory.class));
		LoggingSystem result = mock(LoggingSystem.class);
		given(delegates.get(1).getLoggingSystem(this.classLoader)).willReturn(result);
		DelegatingLoggingSystemFactory factory = new DelegatingLoggingSystemFactory((cl) -> delegates);
		assertThat(factory.getLoggingSystem(this.classLoader)).isSameAs(result);
		verify(delegates.get(0)).getLoggingSystem(this.classLoader);
		verify(delegates.get(1)).getLoggingSystem(this.classLoader);
		verifyNoInteractions(delegates.get(2));
	}

}
