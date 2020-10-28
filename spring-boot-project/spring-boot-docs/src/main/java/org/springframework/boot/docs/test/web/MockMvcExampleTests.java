package org.springframework.boot.docs.test.web;

// tag::test-mock-mvc[]

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MockMvcExampleTests {

	@Test
	void exampleTest(@Autowired MockMvc mvc) throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string("Hello World"));
	}

}
// end::test-mock-mvc[]
