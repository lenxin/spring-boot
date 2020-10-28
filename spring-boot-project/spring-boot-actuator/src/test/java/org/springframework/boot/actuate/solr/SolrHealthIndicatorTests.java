package org.springframework.boot.actuate.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.BaseHttpSolrClient.RemoteSolrException;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.util.NamedList;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests for {@link SolrHealthIndicator}
 *



 */
class SolrHealthIndicatorTests {

	@Test
	void healthWhenSolrStatusUpAndBaseUrlPointsToRootReturnsUp() throws Exception {
		SolrClient solrClient = mock(SolrClient.class);
		given(solrClient.request(any(CoreAdminRequest.class), isNull())).willReturn(mockResponse(0));
		SolrHealthIndicator healthIndicator = new SolrHealthIndicator(solrClient);
		assertHealth(healthIndicator, Status.UP, 0, "root");
		verify(solrClient, times(1)).request(any(CoreAdminRequest.class), isNull());
		verifyNoMoreInteractions(solrClient);
	}

	@Test
	void healthWhenSolrStatusDownAndBaseUrlPointsToRootReturnsDown() throws Exception {
		SolrClient solrClient = mock(SolrClient.class);
		given(solrClient.request(any(CoreAdminRequest.class), isNull())).willReturn(mockResponse(400));
		SolrHealthIndicator healthIndicator = new SolrHealthIndicator(solrClient);
		assertHealth(healthIndicator, Status.DOWN, 400, "root");
		verify(solrClient, times(1)).request(any(CoreAdminRequest.class), isNull());
		verifyNoMoreInteractions(solrClient);
	}

	@Test
	void healthWhenSolrStatusUpAndBaseUrlPointsToParticularCoreReturnsUp() throws Exception {
		SolrClient solrClient = mock(SolrClient.class);
		given(solrClient.request(any(CoreAdminRequest.class), isNull()))
				.willThrow(new RemoteSolrException("mock", 404, "", null));
		given(solrClient.ping()).willReturn(mockPingResponse(0));
		SolrHealthIndicator healthIndicator = new SolrHealthIndicator(solrClient);
		assertHealth(healthIndicator, Status.UP, 0, "particular core");
		verify(solrClient, times(1)).request(any(CoreAdminRequest.class), isNull());
		verify(solrClient, times(1)).ping();
		verifyNoMoreInteractions(solrClient);
	}

	@Test
	void healthWhenSolrStatusDownAndBaseUrlPointsToParticularCoreReturnsDown() throws Exception {
		SolrClient solrClient = mock(SolrClient.class);
		given(solrClient.request(any(CoreAdminRequest.class), isNull()))
				.willThrow(new RemoteSolrException("mock", 404, "", null));
		given(solrClient.ping()).willReturn(mockPingResponse(400));
		SolrHealthIndicator healthIndicator = new SolrHealthIndicator(solrClient);
		assertHealth(healthIndicator, Status.DOWN, 400, "particular core");
		verify(solrClient, times(1)).request(any(CoreAdminRequest.class), isNull());
		verify(solrClient, times(1)).ping();
		verifyNoMoreInteractions(solrClient);
	}

	@Test
	void healthWhenSolrConnectionFailsReturnsDown() throws Exception {
		SolrClient solrClient = mock(SolrClient.class);
		given(solrClient.request(any(CoreAdminRequest.class), isNull()))
				.willThrow(new IOException("Connection failed"));
		SolrHealthIndicator healthIndicator = new SolrHealthIndicator(solrClient);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
		assertThat((String) health.getDetails().get("error")).contains("Connection failed");
		verify(solrClient, times(1)).request(any(CoreAdminRequest.class), isNull());
		verifyNoMoreInteractions(solrClient);
	}

	@Test
	void healthWhenMakingMultipleCallsRemembersStatusStrategy() throws Exception {
		SolrClient solrClient = mock(SolrClient.class);
		given(solrClient.request(any(CoreAdminRequest.class), isNull()))
				.willThrow(new RemoteSolrException("mock", 404, "", null));
		given(solrClient.ping()).willReturn(mockPingResponse(0));
		SolrHealthIndicator healthIndicator = new SolrHealthIndicator(solrClient);
		healthIndicator.health();
		verify(solrClient, times(1)).request(any(CoreAdminRequest.class), isNull());
		verify(solrClient, times(1)).ping();
		verifyNoMoreInteractions(solrClient);
		healthIndicator.health();
		verify(solrClient, times(2)).ping();
		verifyNoMoreInteractions(solrClient);
	}

	private void assertHealth(SolrHealthIndicator healthIndicator, Status expectedStatus, int expectedStatusCode,
			String expectedPathType) {
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(expectedStatus);
		assertThat(health.getDetails().get("status")).isEqualTo(expectedStatusCode);
		assertThat(health.getDetails().get("detectedPathType")).isEqualTo(expectedPathType);
	}

	private NamedList<Object> mockResponse(int status) {
		NamedList<Object> response = new NamedList<>();
		NamedList<Object> headers = new NamedList<>();
		headers.add("status", status);
		response.add("responseHeader", headers);
		return response;
	}

	private SolrPingResponse mockPingResponse(int status) {
		SolrPingResponse pingResponse = new SolrPingResponse();
		pingResponse.setResponse(mockResponse(status));
		return pingResponse;
	}

}
