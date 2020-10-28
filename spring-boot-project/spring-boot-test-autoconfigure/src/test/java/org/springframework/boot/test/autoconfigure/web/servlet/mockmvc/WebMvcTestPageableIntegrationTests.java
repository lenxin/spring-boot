package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link WebMvcTest @WebMvcTest} and Pageable support.
 *

 */
@WebMvcTest
@WithMockUser
class WebMvcTestPageableIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldSupportPageable() throws Exception {
		this.mvc.perform(get("/paged").param("page", "2").param("size", "42")).andExpect(status().isOk())
				.andExpect(content().string("2:42"));
	}

}
