package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} with a disabled filter registration.
 *

 */
@WebMvcTest
class WebMvcTestServletFilterRegistrationDisabledIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldNotApplyFilter() throws Exception {
		this.mvc.perform(get("/one")).andExpect(header().string("x-test", (String) null));
	}

	@TestConfiguration(proxyBeanMethods = false)
	static class DisabledRegistrationConfiguration {

		@Bean
		FilterRegistrationBean<ExampleFilter> exampleFilterRegistration(ExampleFilter filter) {
			FilterRegistrationBean<ExampleFilter> registration = new FilterRegistrationBean<>(filter);
			registration.setEnabled(false);
			return registration;
		}

	}

}
