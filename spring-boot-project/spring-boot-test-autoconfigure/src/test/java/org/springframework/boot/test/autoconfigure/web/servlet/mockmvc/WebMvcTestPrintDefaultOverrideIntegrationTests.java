package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} when a specific controller is defined.
 *

 */
@WebMvcTest
@WithMockUser
@TestPropertySource(properties = "spring.test.mockmvc.print=NONE")
@ExtendWith(OutputCaptureExtension.class)
class WebMvcTestPrintDefaultOverrideIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldFindController1(CapturedOutput output) throws Exception {
		this.mvc.perform(get("/one")).andExpect(content().string("one")).andExpect(status().isOk());
		assertThat(output).doesNotContain("Request URI = /one");
	}

}
