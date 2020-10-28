package org.springframework.boot.docs.test.autoconfigure.restdocs.restassured;

// tag::source[]
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
class UserDocumentationTests {

	@Test
	void listUsers(@Autowired RequestSpecification documentationSpec, @LocalServerPort int port) {
		given(documentationSpec).filter(document("list-users")).when().port(port).get("/").then().assertThat()
				.statusCode(is(200));
	}

}
// end::source[]
