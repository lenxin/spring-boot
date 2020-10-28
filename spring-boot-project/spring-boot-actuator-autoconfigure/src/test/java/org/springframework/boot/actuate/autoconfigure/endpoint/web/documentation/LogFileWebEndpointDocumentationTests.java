package org.springframework.boot.actuate.autoconfigure.endpoint.web.documentation;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.logging.LogFileWebEndpoint;
import org.springframework.boot.logging.LogFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for generating documentation describing the {@link LogFileWebEndpoint}.
 *

 */
@TestPropertySource(
		properties = "logging.file.name=src/test/resources/org/springframework/boot/actuate/autoconfigure/endpoint/web/documentation/sample.log")
class LogFileWebEndpointDocumentationTests extends MockMvcEndpointDocumentationTests {

	@Test
	void logFile() throws Exception {
		this.mockMvc.perform(get("/actuator/logfile")).andExpect(status().isOk())
				.andDo(MockMvcRestDocumentation.document("logfile/entire"));
	}

	@Test
	void logFileRange() throws Exception {
		this.mockMvc.perform(get("/actuator/logfile").header("Range", "bytes=0-1023"))
				.andExpect(status().isPartialContent()).andDo(MockMvcRestDocumentation.document("logfile/range"));
	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseDocumentationConfiguration.class)
	static class TestConfiguration {

		@Bean
		LogFileWebEndpoint endpoint(Environment environment) {
			return new LogFileWebEndpoint(LogFile.get(environment), null);
		}

	}

}
