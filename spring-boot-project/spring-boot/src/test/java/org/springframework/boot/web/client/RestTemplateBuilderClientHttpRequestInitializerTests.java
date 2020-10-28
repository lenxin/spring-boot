package org.springframework.boot.web.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link RestTemplateBuilderClientHttpRequestInitializer}.
 *



 */
public class RestTemplateBuilderClientHttpRequestInitializerTests {

	private final MockClientHttpRequest request = new MockClientHttpRequest();

	@Test
	void createRequestWhenHasBasicAuthAndNoAuthHeaderAddsHeader() throws IOException {
		new RestTemplateBuilderClientHttpRequestInitializer(new BasicAuthentication("spring", "boot", null),
				Collections.emptyMap(), Collections.emptySet()).initialize(this.request);
		assertThat(this.request.getHeaders().get(HttpHeaders.AUTHORIZATION)).containsExactly("Basic c3ByaW5nOmJvb3Q=");
	}

	@Test
	void createRequestWhenHasBasicAuthAndExistingAuthHeaderDoesNotAddHeader() throws IOException {
		this.request.getHeaders().setBasicAuth("boot", "spring");
		new RestTemplateBuilderClientHttpRequestInitializer(new BasicAuthentication("spring", "boot", null),
				Collections.emptyMap(), Collections.emptySet()).initialize(this.request);
		assertThat(this.request.getHeaders().get(HttpHeaders.AUTHORIZATION)).doesNotContain("Basic c3ByaW5nOmJvb3Q=");
	}

	@Test
	void createRequestWhenHasDefaultHeadersAddsMissing() throws IOException {
		this.request.getHeaders().add("one", "existing");
		Map<String, List<String>> defaultHeaders = new LinkedHashMap<>();
		defaultHeaders.put("one", Collections.singletonList("1"));
		defaultHeaders.put("two", Arrays.asList("2", "3"));
		defaultHeaders.put("three", Collections.singletonList("4"));
		new RestTemplateBuilderClientHttpRequestInitializer(null, defaultHeaders, Collections.emptySet())
				.initialize(this.request);
		assertThat(this.request.getHeaders().get("one")).containsExactly("existing");
		assertThat(this.request.getHeaders().get("two")).containsExactly("2", "3");
		assertThat(this.request.getHeaders().get("three")).containsExactly("4");
	}

	@Test
	@SuppressWarnings("unchecked")
	void createRequestWhenHasRequestCustomizersAppliesThemInOrder() throws IOException {
		Set<RestTemplateRequestCustomizer<?>> customizers = new LinkedHashSet<>();
		customizers.add(mock(RestTemplateRequestCustomizer.class));
		customizers.add(mock(RestTemplateRequestCustomizer.class));
		customizers.add(mock(RestTemplateRequestCustomizer.class));
		new RestTemplateBuilderClientHttpRequestInitializer(null, Collections.emptyMap(), customizers)
				.initialize(this.request);
		InOrder inOrder = inOrder(customizers.toArray());
		for (RestTemplateRequestCustomizer<?> customizer : customizers) {
			inOrder.verify((RestTemplateRequestCustomizer<ClientHttpRequest>) customizer).customize(this.request);
		}
	}

}
