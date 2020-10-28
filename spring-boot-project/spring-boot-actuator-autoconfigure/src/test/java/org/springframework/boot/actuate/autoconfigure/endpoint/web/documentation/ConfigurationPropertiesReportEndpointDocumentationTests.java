package org.springframework.boot.actuate.autoconfigure.endpoint.web.documentation;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for generating documentation describing
 * {@link ConfigurationPropertiesReportEndpoint}.
 *

 */
class ConfigurationPropertiesReportEndpointDocumentationTests extends MockMvcEndpointDocumentationTests {

	@Test
	void configProps() throws Exception {
		this.mockMvc.perform(get("/actuator/configprops")).andExpect(status().isOk())
				.andDo(MockMvcRestDocumentation.document("configprops",
						preprocessResponse(limit("contexts", getApplicationContext().getId(), "beans")),
						responseFields(fieldWithPath("contexts").description("Application contexts keyed by id."),
								fieldWithPath("contexts.*.beans.*")
										.description("`@ConfigurationProperties` beans keyed by bean name."),
								fieldWithPath("contexts.*.beans.*.prefix")
										.description("Prefix applied to the names of the bean's properties."),
								subsectionWithPath("contexts.*.beans.*.properties")
										.description("Properties of the bean as name-value pairs."),
								subsectionWithPath("contexts.*.beans.*.inputs").description(
										"Origin and value of the configuration property used when binding to this bean."),
								parentIdField())));
	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseDocumentationConfiguration.class)
	static class TestConfiguration {

		@Bean
		ConfigurationPropertiesReportEndpoint endpoint() {
			return new ConfigurationPropertiesReportEndpoint();
		}

	}

}
