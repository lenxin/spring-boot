package org.springframework.boot.test.autoconfigure.webservices.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceMessageSender;
import org.springframework.ws.test.client.MockWebServiceServer;

/**
 * Auto-configuration for {@link MockWebServiceServer} support.
 *

 * @see AutoConfigureMockWebServiceServer
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.test.webservice.client.mockserver", name = "enabled")
@ConditionalOnClass({ MockWebServiceServer.class, WebServiceTemplate.class })
public class MockWebServiceServerAutoConfiguration {

	@Bean
	public TestMockWebServiceServer mockWebServiceServer() {
		return new TestMockWebServiceServer(new MockWebServiceMessageSender());
	}

	@Bean
	public MockWebServiceServerWebServiceTemplateCustomizer mockWebServiceServerWebServiceTemplateCustomizer(
			TestMockWebServiceServer mockWebServiceServer) {
		return new MockWebServiceServerWebServiceTemplateCustomizer(mockWebServiceServer);
	}

}
