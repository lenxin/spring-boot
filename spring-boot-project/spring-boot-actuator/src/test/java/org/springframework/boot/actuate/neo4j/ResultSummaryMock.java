package org.springframework.boot.actuate.neo4j;

import org.neo4j.driver.summary.DatabaseInfo;
import org.neo4j.driver.summary.ResultSummary;
import org.neo4j.driver.summary.ServerInfo;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Test utility to mock {@link ResultSummary}.
 *

 */
final class ResultSummaryMock {

	private ResultSummaryMock() {
	}

	static ResultSummary createResultSummary(String serverVersion, String serverAddress, String databaseName) {
		ServerInfo serverInfo = mock(ServerInfo.class);
		given(serverInfo.version()).willReturn(serverVersion);
		given(serverInfo.address()).willReturn(serverAddress);
		DatabaseInfo databaseInfo = mock(DatabaseInfo.class);
		given(databaseInfo.name()).willReturn(databaseName);
		ResultSummary resultSummary = mock(ResultSummary.class);
		given(resultSummary.server()).willReturn(serverInfo);
		given(resultSummary.database()).willReturn(databaseInfo);
		return resultSummary;
	}

}
