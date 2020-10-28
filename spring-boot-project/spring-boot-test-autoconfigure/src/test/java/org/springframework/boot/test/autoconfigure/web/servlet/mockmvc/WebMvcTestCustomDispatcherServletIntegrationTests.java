package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.DispatcherServlet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for Test {@link DispatcherServlet} customizations.
 *

 */
@WebMvcTest
@WithMockUser
@TestPropertySource(properties = { "spring.mvc.throw-exception-if-no-handler-found=true",
		"spring.mvc.static-path-pattern=/static/**" })
class WebMvcTestCustomDispatcherServletIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void dispatcherServletIsCustomized() throws Exception {
		this.mvc.perform(get("/does-not-exist")).andExpect(status().isBadRequest())
				.andExpect(content().string("Invalid request: /does-not-exist"));
	}

}
