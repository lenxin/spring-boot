package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

/**
 * Integration tests for {@link WebMvcTest @WebMvcTest} and Spring HATEOAS.
 *

 */
@WebMvcTest
@WithMockUser
class WebMvcTestHateoasIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void plainResponse() throws Exception {
		this.mockMvc.perform(get("/hateoas/plain"))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/json"));
	}

	@Test
	void hateoasResponse() throws Exception {
		this.mockMvc.perform(get("/hateoas/resource"))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/hal+json"));
	}

}
