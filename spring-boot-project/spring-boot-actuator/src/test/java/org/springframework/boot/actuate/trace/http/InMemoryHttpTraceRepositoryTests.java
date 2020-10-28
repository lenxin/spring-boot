package org.springframework.boot.actuate.trace.http;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link InMemoryHttpTraceRepository}.
 *


 */
class InMemoryHttpTraceRepositoryTests {

	private final InMemoryHttpTraceRepository repository = new InMemoryHttpTraceRepository();

	@Test
	void capacityLimited() {
		this.repository.setCapacity(2);
		this.repository.add(new HttpTrace(createRequest("GET")));
		this.repository.add(new HttpTrace(createRequest("POST")));
		this.repository.add(new HttpTrace(createRequest("DELETE")));
		List<HttpTrace> traces = this.repository.findAll();
		assertThat(traces).hasSize(2);
		assertThat(traces.get(0).getRequest().getMethod()).isEqualTo("DELETE");
		assertThat(traces.get(1).getRequest().getMethod()).isEqualTo("POST");
	}

	@Test
	void reverseFalse() {
		this.repository.setReverse(false);
		this.repository.setCapacity(2);
		this.repository.add(new HttpTrace(createRequest("GET")));
		this.repository.add(new HttpTrace(createRequest("POST")));
		this.repository.add(new HttpTrace(createRequest("DELETE")));
		List<HttpTrace> traces = this.repository.findAll();
		assertThat(traces).hasSize(2);
		assertThat(traces.get(0).getRequest().getMethod()).isEqualTo("POST");
		assertThat(traces.get(1).getRequest().getMethod()).isEqualTo("DELETE");
	}

	private TraceableRequest createRequest(String method) {
		TraceableRequest request = mock(TraceableRequest.class);
		given(request.getMethod()).willReturn(method);
		return request;
	}

}
