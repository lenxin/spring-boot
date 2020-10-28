package org.springframework.boot.actuate.autoconfigure.endpoint.web.documentation;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for generating documentation describing the {@link ShutdownEndpoint}.
 *

 */
class ShutdownEndpointDocumentationTests extends MockMvcEndpointDocumentationTests {

	@Test
	void shutdown() throws Exception {
		this.mockMvc.perform(post("/actuator/shutdown")).andExpect(status().isOk())
				.andDo(MockMvcRestDocumentation.document("shutdown", responseFields(
						fieldWithPath("message").description("Message describing the result of the request."))));
	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseDocumentationConfiguration.class)
	static class TestConfiguration {

		@Bean
		ShutdownEndpoint endpoint(Environment environment) {
			ShutdownEndpoint endpoint = new ShutdownEndpoint();
			endpoint.setApplicationContext(new AnnotationConfigApplicationContext());
			return endpoint;
		}

	}

}
