package org.springframework.boot.test.autoconfigure.restdocs;

import java.io.File;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.testsupport.BuildOutput;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.FileSystemUtils;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

/**
 * Integration tests for {@link RestDocsAutoConfiguration} with REST Assured.
 *

 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
class RestAssuredRestDocsAutoConfigurationIntegrationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private RequestSpecification documentationSpec;

	private File generatedSnippets;

	@BeforeEach
	void deleteSnippets() {
		this.generatedSnippets = new File(new BuildOutput(getClass()).getRootLocation(), "generated-snippets");
		FileSystemUtils.deleteRecursively(this.generatedSnippets);
	}

	@Test
	void defaultSnippetsAreWritten() {
		given(this.documentationSpec)
				.filter(document("default-snippets",
						preprocessRequest(modifyUris().scheme("https").host("api.example.com").removePort())))
				.when().port(this.port).get("/").then().assertThat().statusCode(is(200));
		File defaultSnippetsDir = new File(this.generatedSnippets, "default-snippets");
		assertThat(defaultSnippetsDir).exists();
		assertThat(contentOf(new File(defaultSnippetsDir, "curl-request.adoc"))).contains("'https://api.example.com/'");
		assertThat(contentOf(new File(defaultSnippetsDir, "http-request.adoc"))).contains("api.example.com");
		assertThat(new File(defaultSnippetsDir, "http-response.adoc")).isFile();
	}

}
