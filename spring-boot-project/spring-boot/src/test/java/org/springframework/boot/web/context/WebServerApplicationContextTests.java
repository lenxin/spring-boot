package org.springframework.boot.web.context;

import org.junit.jupiter.api.Test;

import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link WebServerApplicationContext}.
 *

 */
public class WebServerApplicationContextTests {

	@Test
	void hasServerNamespaceWhenContextIsNotWebServerApplicationContextReturnsFalse() {
		ApplicationContext context = mock(ApplicationContext.class);
		assertThat(WebServerApplicationContext.hasServerNamespace(context, "test")).isFalse();
	}

	@Test
	void hasServerNamespaceWhenContextIsWebServerApplicationContextAndNamespaceDoesNotMatchReturnsFalse() {
		ApplicationContext context = mock(WebServerApplicationContext.class);
		assertThat(WebServerApplicationContext.hasServerNamespace(context, "test")).isFalse();
	}

	@Test
	void hasServerNamespaceWhenContextIsWebServerApplicationContextAndNamespaceMatchesReturnsTrue() {
		WebServerApplicationContext context = mock(WebServerApplicationContext.class);
		given(context.getServerNamespace()).willReturn("test");
		assertThat(WebServerApplicationContext.hasServerNamespace(context, "test")).isTrue();
	}

}
