package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} to validate converters are discovered.
 *

 */
@WebMvcTest(controllers = ExampleController2.class)
@WithMockUser
class WebMvcTestConverterIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldFindConverter() throws Exception {
		String id = UUID.randomUUID().toString();
		this.mvc.perform(get("/two/" + id)).andExpect(content().string(id + "two")).andExpect(status().isOk());
	}

}
