package org.springframework.boot.actuate.autoconfigure.endpoint.web.documentation;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.integration.IntegrationGraphEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.graph.IntegrationGraphServer;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for generating documentation describing the {@link IntegrationGraphEndpoint}.
 *

 */
class IntegrationGraphEndpointDocumentationTests extends MockMvcEndpointDocumentationTests {

	@Test
	void graph() throws Exception {
		this.mockMvc.perform(get("/actuator/integrationgraph")).andExpect(status().isOk())
				.andDo(MockMvcRestDocumentation.document("integrationgraph/graph"));
	}

	@Test
	void rebuild() throws Exception {
		this.mockMvc.perform(post("/actuator/integrationgraph")).andExpect(status().isNoContent())
				.andDo(MockMvcRestDocumentation.document("integrationgraph/rebuild"));
	}

	@Configuration(proxyBeanMethods = false)
	@EnableIntegration
	@Import(BaseDocumentationConfiguration.class)
	static class TestConfiguration {

		@Bean
		IntegrationGraphServer integrationGraphServer() {
			return new IntegrationGraphServer();
		}

		@Bean
		IntegrationGraphEndpoint endpoint(IntegrationGraphServer integrationGraphServer) {
			return new IntegrationGraphEndpoint(integrationGraphServer);
		}

	}

}
