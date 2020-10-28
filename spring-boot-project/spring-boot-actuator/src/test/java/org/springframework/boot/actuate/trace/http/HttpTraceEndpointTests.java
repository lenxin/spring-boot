package org.springframework.boot.actuate.trace.http;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link HttpTraceEndpoint}.
 *


 */
class HttpTraceEndpointTests {

	@Test
	void trace() {
		HttpTraceRepository repository = new InMemoryHttpTraceRepository();
		repository.add(new HttpTrace(createRequest("GET")));
		List<HttpTrace> traces = new HttpTraceEndpoint(repository).traces().getTraces();
		assertThat(traces).hasSize(1);
		HttpTrace trace = traces.get(0);
		assertThat(trace.getRequest().getMethod()).isEqualTo("GET");
	}

	private TraceableRequest createRequest(String method) {
		TraceableRequest request = mock(TraceableRequest.class);
		given(request.getMethod()).willReturn(method);
		return request;
	}

}
