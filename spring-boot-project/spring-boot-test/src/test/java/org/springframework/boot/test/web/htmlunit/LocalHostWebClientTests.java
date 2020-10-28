package org.springframework.boot.test.web.htmlunit;

import java.io.IOException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebConnection;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link LocalHostWebClient}.
 *

 */
@SuppressWarnings("resource")
@ExtendWith(MockitoExtension.class)
class LocalHostWebClientTests {

	@Captor
	private ArgumentCaptor<WebRequest> requestCaptor;

	@Test
	void createWhenEnvironmentIsNullWillThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LocalHostWebClient(null))
				.withMessageContaining("Environment must not be null");
	}

	@Test
	void getPageWhenUrlIsRelativeAndNoPortWillUseLocalhost8080() throws Exception {
		MockEnvironment environment = new MockEnvironment();
		WebClient client = new LocalHostWebClient(environment);
		WebConnection connection = mockConnection();
		client.setWebConnection(connection);
		client.getPage("/test");
		verify(connection).getResponse(this.requestCaptor.capture());
		assertThat(this.requestCaptor.getValue().getUrl()).isEqualTo(new URL("http://localhost:8080/test"));
	}

	@Test
	void getPageWhenUrlIsRelativeAndHasPortWillUseLocalhostPort() throws Exception {
		MockEnvironment environment = new MockEnvironment();
		environment.setProperty("local.server.port", "8181");
		WebClient client = new LocalHostWebClient(environment);
		WebConnection connection = mockConnection();
		client.setWebConnection(connection);
		client.getPage("/test");
		verify(connection).getResponse(this.requestCaptor.capture());
		assertThat(this.requestCaptor.getValue().getUrl()).isEqualTo(new URL("http://localhost:8181/test"));
	}

	private WebConnection mockConnection() throws IOException {
		WebConnection connection = mock(WebConnection.class);
		WebResponse response = new StringWebResponse("test", new URL("http://localhost"));
		given(connection.getResponse(any())).willReturn(response);
		return connection;
	}

}
