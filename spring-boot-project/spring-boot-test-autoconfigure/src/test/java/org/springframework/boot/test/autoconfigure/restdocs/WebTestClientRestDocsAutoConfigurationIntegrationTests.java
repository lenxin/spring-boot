package org.springframework.boot.test.autoconfigure.restdocs;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.testsupport.BuildOutput;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.FileSystemUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

/**
 * Integration tests for {@link RestDocsAutoConfiguration} with {@link WebTestClient}.
 *

 */
@WebFluxTest
@WithMockUser
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.example.com", uriPort = 443)
class WebTestClientRestDocsAutoConfigurationIntegrationTests {

	@Autowired
	private WebTestClient webTestClient;

	private File generatedSnippets;

	@BeforeEach
	void deleteSnippets() {
		this.generatedSnippets = new File(new BuildOutput(getClass()).getRootLocation(), "generated-snippets");
		FileSystemUtils.deleteRecursively(this.generatedSnippets);
	}

	@Test
	void defaultSnippetsAreWritten() throws Exception {
		this.webTestClient.get().uri("/").exchange().expectStatus().is2xxSuccessful().expectBody()
				.consumeWith(document("default-snippets"));
		File defaultSnippetsDir = new File(this.generatedSnippets, "default-snippets");
		assertThat(defaultSnippetsDir).exists();
		assertThat(contentOf(new File(defaultSnippetsDir, "curl-request.adoc"))).contains("'https://api.example.com/'");
		assertThat(contentOf(new File(defaultSnippetsDir, "http-request.adoc"))).contains("api.example.com");
		assertThat(new File(defaultSnippetsDir, "http-response.adoc")).isFile();
	}

}
