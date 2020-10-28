package org.springframework.boot.web.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link RootUriTemplateHandler}.
 *

 */
@ExtendWith(MockitoExtension.class)
class RootUriTemplateHandlerTests {

	private URI uri;

	@Mock
	public UriTemplateHandler delegate;

	public UriTemplateHandler handler;

	@BeforeEach
	void setup() throws URISyntaxException {
		this.uri = new URI("https://example.com/hello");
		this.handler = new RootUriTemplateHandler("https://example.com", this.delegate);
	}

	@Test
	void createWithNullRootUriShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new RootUriTemplateHandler((String) null))
				.withMessageContaining("RootUri must not be null");
	}

	@Test
	void createWithNullHandlerShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new RootUriTemplateHandler("https://example.com", null))
				.withMessageContaining("Handler must not be null");
	}

	@Test
	@SuppressWarnings("unchecked")
	void expandMapVariablesShouldPrefixRoot() {
		given(this.delegate.expand(anyString(), any(Map.class))).willReturn(this.uri);
		HashMap<String, Object> uriVariables = new HashMap<>();
		URI expanded = this.handler.expand("/hello", uriVariables);
		verify(this.delegate).expand("https://example.com/hello", uriVariables);
		assertThat(expanded).isEqualTo(this.uri);
	}

	@Test
	@SuppressWarnings("unchecked")
	void expandMapVariablesWhenPathDoesNotStartWithSlashShouldNotPrefixRoot() {
		given(this.delegate.expand(anyString(), any(Map.class))).willReturn(this.uri);
		HashMap<String, Object> uriVariables = new HashMap<>();
		URI expanded = this.handler.expand("https://spring.io/hello", uriVariables);
		verify(this.delegate).expand("https://spring.io/hello", uriVariables);
		assertThat(expanded).isEqualTo(this.uri);
	}

	@Test
	void expandArrayVariablesShouldPrefixRoot() {
		given(this.delegate.expand(anyString(), any(Object[].class))).willReturn(this.uri);
		Object[] uriVariables = new Object[0];
		URI expanded = this.handler.expand("/hello", uriVariables);
		verify(this.delegate).expand("https://example.com/hello", uriVariables);
		assertThat(expanded).isEqualTo(this.uri);
	}

	@Test
	void expandArrayVariablesWhenPathDoesNotStartWithSlashShouldNotPrefixRoot() {
		given(this.delegate.expand(anyString(), any(Object[].class))).willReturn(this.uri);
		Object[] uriVariables = new Object[0];
		URI expanded = this.handler.expand("https://spring.io/hello", uriVariables);
		verify(this.delegate).expand("https://spring.io/hello", uriVariables);
		assertThat(expanded).isEqualTo(this.uri);
	}

	@Test
	void applyShouldWrapExistingTemplate() {
		given(this.delegate.expand(anyString(), any(Object[].class))).willReturn(this.uri);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setUriTemplateHandler(this.delegate);
		this.handler = RootUriTemplateHandler.addTo(restTemplate, "https://example.com");
		Object[] uriVariables = new Object[0];
		URI expanded = this.handler.expand("/hello", uriVariables);
		verify(this.delegate).expand("https://example.com/hello", uriVariables);
		assertThat(expanded).isEqualTo(this.uri);
	}

}
