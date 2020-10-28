package org.springframework.boot.actuate.endpoint.annotation;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvokerAdvisor;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link DiscovererEndpointFilter}.
 *

 */
class DiscovererEndpointFilterTests {

	@Test
	void createWhenDiscovererIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new TestDiscovererEndpointFilter(null))
				.withMessageContaining("Discoverer must not be null");
	}

	@Test
	void matchWhenDiscoveredByDiscovererShouldReturnTrue() {
		DiscovererEndpointFilter filter = new TestDiscovererEndpointFilter(TestDiscovererA.class);
		DiscoveredEndpoint<?> endpoint = mockDiscoveredEndpoint(TestDiscovererA.class);
		assertThat(filter.match(endpoint)).isTrue();
	}

	@Test
	void matchWhenNotDiscoveredByDiscovererShouldReturnFalse() {
		DiscovererEndpointFilter filter = new TestDiscovererEndpointFilter(TestDiscovererA.class);
		DiscoveredEndpoint<?> endpoint = mockDiscoveredEndpoint(TestDiscovererB.class);
		assertThat(filter.match(endpoint)).isFalse();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private DiscoveredEndpoint<?> mockDiscoveredEndpoint(Class<?> discoverer) {
		DiscoveredEndpoint endpoint = mock(DiscoveredEndpoint.class);
		given(endpoint.wasDiscoveredBy(discoverer)).willReturn(true);
		return endpoint;
	}

	static class TestDiscovererEndpointFilter extends DiscovererEndpointFilter {

		TestDiscovererEndpointFilter(Class<? extends EndpointDiscoverer<?, ?>> discoverer) {
			super(discoverer);
		}

	}

	abstract static class TestDiscovererA extends EndpointDiscoverer<ExposableEndpoint<Operation>, Operation> {

		TestDiscovererA(ApplicationContext applicationContext, ParameterValueMapper parameterValueMapper,
				Collection<OperationInvokerAdvisor> invokerAdvisors,
				Collection<EndpointFilter<ExposableEndpoint<Operation>>> filters) {
			super(applicationContext, parameterValueMapper, invokerAdvisors, filters);
		}

	}

	abstract static class TestDiscovererB extends EndpointDiscoverer<ExposableEndpoint<Operation>, Operation> {

		TestDiscovererB(ApplicationContext applicationContext, ParameterValueMapper parameterValueMapper,
				Collection<OperationInvokerAdvisor> invokerAdvisors,
				Collection<EndpointFilter<ExposableEndpoint<Operation>>> filters) {
			super(applicationContext, parameterValueMapper, invokerAdvisors, filters);
		}

	}

}
