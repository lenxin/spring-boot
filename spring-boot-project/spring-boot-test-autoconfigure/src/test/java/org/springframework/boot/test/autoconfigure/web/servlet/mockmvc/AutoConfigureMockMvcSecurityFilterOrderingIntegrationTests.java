package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link AutoConfigureMockMvc @AutoConfigureMockMvc} and the ordering of Spring
 * Security's filter
 *

 */
@WebMvcTest
@WithMockUser(username = "user", password = "secret")
@Import(AfterSecurityFilter.class)
class AutoConfigureMockMvcSecurityFilterOrderingIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void afterSecurityFilterShouldFindAUserPrincipal() throws Exception {
		this.mvc.perform(get("/one")).andExpect(status().isOk()).andExpect(content().string("user"));
	}

}
