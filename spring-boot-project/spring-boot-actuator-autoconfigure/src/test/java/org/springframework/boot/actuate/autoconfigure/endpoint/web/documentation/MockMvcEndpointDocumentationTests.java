package org.springframework.boot.actuate.autoconfigure.endpoint.web.documentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Abstract base class for tests that generate endpoint documentation using Spring REST
 * Docs and {@link MockMvc}.
 *

 */
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public abstract class MockMvcEndpointDocumentationTests extends AbstractEndpointDocumentationTests {

	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext applicationContext;

	@BeforeEach
	void setup(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext)
				.apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation).uris()).build();
	}

	protected WebApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

}
