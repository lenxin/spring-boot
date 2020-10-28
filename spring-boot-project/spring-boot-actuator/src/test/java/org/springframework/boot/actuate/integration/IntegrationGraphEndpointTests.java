package org.springframework.boot.actuate.integration;

import org.junit.jupiter.api.Test;

import org.springframework.integration.graph.Graph;
import org.springframework.integration.graph.IntegrationGraphServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link IntegrationGraphEndpoint}.
 *

 */
class IntegrationGraphEndpointTests {

	private final IntegrationGraphServer server = mock(IntegrationGraphServer.class);

	private final IntegrationGraphEndpoint endpoint = new IntegrationGraphEndpoint(this.server);

	@Test
	void readOperationShouldReturnGraph() {
		Graph mockedGraph = mock(Graph.class);
		given(this.server.getGraph()).willReturn(mockedGraph);
		Graph graph = this.endpoint.graph();
		verify(this.server).getGraph();
		assertThat(graph).isEqualTo(mockedGraph);
	}

	@Test
	void writeOperationShouldRebuildGraph() {
		this.endpoint.rebuild();
		verify(this.server).rebuild();
	}

}
