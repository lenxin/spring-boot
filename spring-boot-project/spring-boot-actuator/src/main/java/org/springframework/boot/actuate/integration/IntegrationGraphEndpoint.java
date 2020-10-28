package org.springframework.boot.actuate.integration;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.integration.graph.Graph;
import org.springframework.integration.graph.IntegrationGraphServer;

/**
 * {@link Endpoint @Endpoint} to expose the Spring Integration graph.
 *

 * @since 2.1.0
 */
@Endpoint(id = "integrationgraph")
public class IntegrationGraphEndpoint {

	private final IntegrationGraphServer graphServer;

	/**
	 * Create a new {@code IntegrationGraphEndpoint} instance that exposes a graph
	 * containing all the Spring Integration components in the given
	 * {@link IntegrationGraphServer}.
	 * @param graphServer the integration graph server
	 */
	public IntegrationGraphEndpoint(IntegrationGraphServer graphServer) {
		this.graphServer = graphServer;
	}

	@ReadOperation
	public Graph graph() {
		return this.graphServer.getGraph();
	}

	@WriteOperation
	public void rebuild() {
		this.graphServer.rebuild();
	}

}
