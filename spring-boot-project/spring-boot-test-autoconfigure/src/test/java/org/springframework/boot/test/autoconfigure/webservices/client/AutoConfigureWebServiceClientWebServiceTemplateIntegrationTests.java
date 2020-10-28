package org.springframework.boot.test.autoconfigure.webservices.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

/**
 * Tests for {@link AutoConfigureWebServiceClient @AutoConfigureWebServiceClient} with
 * {@code registerWebServiceTemplate=true}.
 *

 */
@SpringBootTest
@AutoConfigureWebServiceClient(registerWebServiceTemplate = true)
@AutoConfigureMockWebServiceServer
class AutoConfigureWebServiceClientWebServiceTemplateIntegrationTests {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	@Autowired
	private MockWebServiceServer server;

	@Test
	void webServiceTemplateTest() {
		this.server.expect(payload(new StringSource("<request/>")))
				.andRespond(withPayload(new StringSource("<response/>")));
		this.webServiceTemplate.marshalSendAndReceive("https://example.com", new Request());
	}

	@Configuration(proxyBeanMethods = false)
	@Import(WebServiceMarshallerConfiguration.class)
	static class Config {

	}

}
