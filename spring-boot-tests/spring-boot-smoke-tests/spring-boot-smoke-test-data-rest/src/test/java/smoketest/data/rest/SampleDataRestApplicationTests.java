package smoketest.data.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test to run the application.
 *


 */
@SpringBootTest
// Separate profile for web tests to avoid clashing databases
class SampleDataRestApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	void testHome() throws Exception {
		this.mvc.perform(get("/api")).andExpect(status().isOk()).andExpect(content().string(containsString("hotels")));
	}

	@Test
	void findByNameAndCountry() throws Exception {
		this.mvc.perform(get("/api/cities/search/findByNameAndCountryAllIgnoringCase?name=Melbourne&country=Australia"))
				.andExpect(status().isOk()).andExpect(jsonPath("state", equalTo("Victoria")))
				.andExpect(jsonPath("name", equalTo("Melbourne")));
	}

	@Test
	void findByContaining() throws Exception {
		this.mvc.perform(
				get("/api/cities/search/findByNameContainingAndCountryContainingAllIgnoringCase?name=&country=UK"))
				.andExpect(status().isOk()).andExpect(jsonPath("_embedded.cities", hasSize(3)));
	}

}
