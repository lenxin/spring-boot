package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} with {@link WebClient}.
 *

 */
@WebMvcTest
@WithMockUser
class WebMvcTestWebClientIntegrationTests {

	@Autowired
	private WebClient webClient;

	@Test
	void shouldAutoConfigureWebClient() throws Exception {
		HtmlPage page = this.webClient.getPage("/html");
		assertThat(page.getBody().getTextContent()).isEqualTo("Hello");
	}

}
