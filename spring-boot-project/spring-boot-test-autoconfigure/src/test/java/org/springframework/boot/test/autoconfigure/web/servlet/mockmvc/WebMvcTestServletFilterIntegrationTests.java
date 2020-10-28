package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} servlet filter registration.
 *

 */
@WebMvcTest
class WebMvcTestServletFilterIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldApplyFilter() throws Exception {
		this.mvc.perform(get("/one")).andExpect(header().string("x-test", "abc"));
	}

}
