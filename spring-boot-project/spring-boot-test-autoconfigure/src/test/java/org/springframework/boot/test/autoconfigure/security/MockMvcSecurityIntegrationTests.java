package org.springframework.boot.test.autoconfigure.security;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for MockMvc security.
 *

 */
@WebMvcTest
@TestPropertySource(properties = { "debug=true" })
class MockMvcSecurityIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "test", password = "test", roles = "USER")
	void okResponseWithMockUser() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	void unauthorizedResponseWithNoUser() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	void okResponseWithBasicAuthCredentialsForKnownUser() throws Exception {
		this.mockMvc.perform(get("/").header(HttpHeaders.AUTHORIZATION,
				"Basic " + Base64Utils.encodeToString("user:secret".getBytes()))).andExpect(status().isOk());
	}

}
