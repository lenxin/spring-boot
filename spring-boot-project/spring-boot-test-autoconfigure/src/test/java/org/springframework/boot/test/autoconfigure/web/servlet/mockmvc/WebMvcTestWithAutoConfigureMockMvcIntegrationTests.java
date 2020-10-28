package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} with
 * {@link AutoConfigureMockMvc @AutoConfigureMockMvc}.
 *


 */
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false, webClientEnabled = false, webDriverEnabled = false)
class WebMvcTestWithAutoConfigureMockMvcIntegrationTests {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldNotAddFilters() throws Exception {
		this.mvc.perform(get("/one")).andExpect(header().doesNotExist("x-test"));
	}

	@Test
	void shouldNotHaveWebDriver() {
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.context.getBean(WebDriver.class));
	}

	@Test
	void shouldNotHaveWebClient() {
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.context.getBean(WebClient.class));
	}

}
