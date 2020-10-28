package org.springframework.boot.test.autoconfigure.webservices.client;

import org.springframework.ws.test.client.MockWebServiceMessageSender;
import org.springframework.ws.test.client.MockWebServiceServer;

/**
 * Test {@link MockWebServiceServer} which provides access to the underlying
 * {@link MockWebServiceMessageSender}.
 *

 */
final class TestMockWebServiceServer extends MockWebServiceServer {

	private final MockWebServiceMessageSender mockMessageSender;

	TestMockWebServiceServer(MockWebServiceMessageSender mockMessageSender) {
		super(mockMessageSender);
		this.mockMessageSender = mockMessageSender;
	}

	MockWebServiceMessageSender getMockMessageSender() {
		return this.mockMessageSender;
	}

}
