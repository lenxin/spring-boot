package org.springframework.boot.actuate.autoconfigure.endpoint.web.documentation;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.condition.ConditionsReportEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for generating documentation describing {@link ConditionsReportEndpoint}.
 *

 */
class ConditionsReportEndpointDocumentationTests extends MockMvcEndpointDocumentationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext applicationContext;

	@Override
	@BeforeEach
	void setup(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext)
				.apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation).uris()).build();
	}

	@Test
	void conditions() throws Exception {
		List<FieldDescriptor> positiveMatchFields = Arrays.asList(
				fieldWithPath("").description("Classes and methods with conditions that were matched."),
				fieldWithPath(".*.[].condition").description("Name of the condition."),
				fieldWithPath(".*.[].message").description("Details of why the condition was matched."));
		List<FieldDescriptor> negativeMatchFields = Arrays.asList(
				fieldWithPath("").description("Classes and methods with conditions that were not matched."),
				fieldWithPath(".*.notMatched").description("Conditions that were matched."),
				fieldWithPath(".*.notMatched.[].condition").description("Name of the condition."),
				fieldWithPath(".*.notMatched.[].message").description("Details of why the condition was not matched."),
				fieldWithPath(".*.matched").description("Conditions that were matched."),
				fieldWithPath(".*.matched.[].condition").description("Name of the condition.")
						.type(JsonFieldType.STRING).optional(),
				fieldWithPath(".*.matched.[].message").description("Details of why the condition was matched.")
						.type(JsonFieldType.STRING).optional());
		FieldDescriptor unconditionalClassesField = fieldWithPath("contexts.*.unconditionalClasses")
				.description("Names of unconditional auto-configuration classes if any.");
		this.mockMvc.perform(get("/actuator/conditions")).andExpect(status().isOk())
				.andDo(MockMvcRestDocumentation.document("conditions",
						preprocessResponse(limit("contexts", getApplicationContext().getId(), "positiveMatches"),
								limit("contexts", getApplicationContext().getId(), "negativeMatches")),
						responseFields(fieldWithPath("contexts").description("Application contexts keyed by id."))
								.andWithPrefix("contexts.*.positiveMatches", positiveMatchFields)
								.andWithPrefix("contexts.*.negativeMatches", negativeMatchFields)
								.and(unconditionalClassesField, parentIdField())));
	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseDocumentationConfiguration.class)
	static class TestConfiguration {

		@Bean
		ConditionsReportEndpoint autoConfigurationReportEndpoint(ConfigurableApplicationContext context) {
			ConditionEvaluationReport conditionEvaluationReport = ConditionEvaluationReport
					.get(context.getBeanFactory());
			conditionEvaluationReport
					.recordEvaluationCandidates(Arrays.asList(PropertyPlaceholderAutoConfiguration.class.getName()));
			return new ConditionsReportEndpoint(context);
		}

	}

}
