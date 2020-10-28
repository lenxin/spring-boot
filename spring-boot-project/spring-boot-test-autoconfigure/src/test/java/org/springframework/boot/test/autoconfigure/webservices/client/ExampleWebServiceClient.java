package org.springframework.boot.test.autoconfigure.webservices.client;

import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Example web client used with {@link WebServiceClientTest @WebServiceClientTest} tests.
 *

 */
@Service
public class ExampleWebServiceClient {

	private final WebServiceTemplate webServiceTemplate;

	public ExampleWebServiceClient(WebServiceTemplateBuilder builder) {
		this.webServiceTemplate = builder.build();
	}

	public Response test() {
		return (Response) this.webServiceTemplate.marshalSendAndReceive("https://example.com", new Request());
	}

}
