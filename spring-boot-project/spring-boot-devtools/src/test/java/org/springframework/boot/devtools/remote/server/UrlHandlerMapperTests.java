package org.springframework.boot.devtools.remote.server;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link UrlHandlerMapper}.
 *


 */
class UrlHandlerMapperTests {

	private Handler handler = mock(Handler.class);

	@Test
	void requestUriMustNotBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new UrlHandlerMapper(null, this.handler))
				.withMessageContaining("URL must not be empty");
	}

	@Test
	void requestUriMustNotBeEmpty() {
		assertThatIllegalArgumentException().isThrownBy(() -> new UrlHandlerMapper("", this.handler))
				.withMessageContaining("URL must not be empty");
	}

	@Test
	void requestUrlMustStartWithSlash() {
		assertThatIllegalArgumentException().isThrownBy(() -> new UrlHandlerMapper("tunnel", this.handler))
				.withMessageContaining("URL must start with '/'");
	}

	@Test
	void handlesMatchedUrl() {
		UrlHandlerMapper mapper = new UrlHandlerMapper("/tunnel", this.handler);
		HttpServletRequest servletRequest = new MockHttpServletRequest("GET", "/tunnel");
		ServerHttpRequest request = new ServletServerHttpRequest(servletRequest);
		assertThat(mapper.getHandler(request)).isEqualTo(this.handler);
	}

	@Test
	void ignoresDifferentUrl() {
		UrlHandlerMapper mapper = new UrlHandlerMapper("/tunnel", this.handler);
		HttpServletRequest servletRequest = new MockHttpServletRequest("GET", "/tunnel/other");
		ServerHttpRequest request = new ServletServerHttpRequest(servletRequest);
		assertThat(mapper.getHandler(request)).isNull();
	}

}
