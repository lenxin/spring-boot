package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} when no explicit controller is defined.
 *


 */
@WebMvcTest
@WithMockUser
class WebMvcTestAllControllersIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired(required = false)
	private ErrorAttributes errorAttributes;

	@Test
	void shouldFindController1() throws Exception {
		this.mvc.perform(get("/one")).andExpect(content().string("one")).andExpect(status().isOk());
	}

	@Test
	void shouldFindController2() throws Exception {
		this.mvc.perform(get("/two")).andExpect(content().string("hellotwo")).andExpect(status().isOk());
	}

	@Test
	void shouldFindControllerAdvice() throws Exception {
		this.mvc.perform(get("/error")).andExpect(content().string("recovered")).andExpect(status().isOk());
	}

	@Test
	void shouldRunValidationSuccess() throws Exception {
		this.mvc.perform(get("/three/OK")).andExpect(status().isOk()).andExpect(content().string("Hello OK"));
	}

	@Test
	void shouldRunValidationFailure() throws Exception {
		assertThatExceptionOfType(NestedServletException.class)
				.isThrownBy(() -> this.mvc.perform(get("/three/invalid")))
				.withCauseInstanceOf(ConstraintViolationException.class);
	}

	@Test
	void shouldNotFilterErrorAttributes() {
		assertThat(this.errorAttributes).isNotNull();

	}

}
