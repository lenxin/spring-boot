package org.springframework.boot.actuate.autoconfigure.endpoint.web.documentation;

import java.io.FileWriter;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.management.HeapDumpWebEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.cli.CurlRequestSnippet;
import org.springframework.restdocs.operation.Operation;
import org.springframework.util.FileCopyUtils;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for generating documentation describing the {@link HeapDumpWebEndpoint}.
 *

 */
class HeapDumpWebEndpointDocumentationTests extends MockMvcEndpointDocumentationTests {

	@Test
	void heapDump() throws Exception {
		this.mockMvc.perform(get("/actuator/heapdump")).andExpect(status().isOk())
				.andDo(document("heapdump", new CurlRequestSnippet(CliDocumentation.multiLineFormat()) {

					@Override
					protected Map<String, Object> createModel(Operation operation) {
						Map<String, Object> model = super.createModel(operation);
						model.put("options", "-O");
						return model;
					}

				}));
	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseDocumentationConfiguration.class)
	static class TestConfiguration {

		@Bean
		HeapDumpWebEndpoint endpoint() {
			return new HeapDumpWebEndpoint() {

				@Override
				protected HeapDumper createHeapDumper() throws HeapDumperUnavailableException {
					return (file, live) -> FileCopyUtils.copy("<<binary content>>", new FileWriter(file));
				}

			};
		}

	}

}
