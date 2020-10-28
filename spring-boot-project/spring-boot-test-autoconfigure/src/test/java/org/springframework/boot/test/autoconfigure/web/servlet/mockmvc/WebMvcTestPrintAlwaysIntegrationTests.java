package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} default print output.
 *

 */
@WebMvcTest
@WithMockUser
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ExtendWith(OutputCaptureExtension.class)
class WebMvcTestPrintAlwaysIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldPrint(CapturedOutput output) throws Exception {
		this.mvc.perform(get("/one")).andExpect(content().string("one")).andExpect(status().isOk());
		assertThat(output).contains("Request URI = /one");
	}

}
