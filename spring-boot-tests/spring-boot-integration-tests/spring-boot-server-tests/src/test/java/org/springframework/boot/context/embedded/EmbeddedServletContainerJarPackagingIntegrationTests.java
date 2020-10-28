package org.springframework.boot.context.embedded;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for Spring Boot's embedded servlet container support using jar
 * packaging.
 *

 */
@EmbeddedServletContainerTest(packaging = "jar",
		launchers = { PackagedApplicationLauncher.class, ExplodedApplicationLauncher.class })
class EmbeddedServletContainerJarPackagingIntegrationTests {

	@TestTemplate
	void nestedMetaInfResourceIsAvailableViaHttp(RestTemplate rest) {
		ResponseEntity<String> entity = rest.getForEntity("/nested-meta-inf-resource.txt", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@TestTemplate
	@DisabledOnOs(OS.WINDOWS)
	void nestedMetaInfResourceWithNameThatContainsReservedCharactersIsAvailableViaHttp(RestTemplate rest) {
		ResponseEntity<String> entity = rest.getForEntity(
				"/nested-reserved-%21%23%24%25%26%28%29%2A%2B%2C%3A%3D%3F%40%5B%5D-meta-inf-resource.txt",
				String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isEqualTo("encoded-name");
	}

	@TestTemplate
	void nestedMetaInfResourceIsAvailableViaServletContext(RestTemplate rest) {
		ResponseEntity<String> entity = rest.getForEntity("/servletContext?/nested-meta-inf-resource.txt",
				String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@TestTemplate
	void nestedJarIsNotAvailableViaHttp(RestTemplate rest) {
		ResponseEntity<String> entity = rest.getForEntity("/BOOT-INF/lib/resources-1.0.jar", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@TestTemplate
	void applicationClassesAreNotAvailableViaHttp(RestTemplate rest) {
		ResponseEntity<String> entity = rest
				.getForEntity("/BOOT-INF/classes/com/example/ResourceHandlingApplication.class", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@TestTemplate
	void launcherIsNotAvailableViaHttp(RestTemplate rest) {
		ResponseEntity<String> entity = rest.getForEntity("/org/springframework/boot/loader/Launcher.class",
				String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@TestTemplate
	void conditionalOnWarDeploymentBeanIsNotAvailableForEmbeddedServer(RestTemplate rest) {
		ResponseEntity<String> entity = rest.getForEntity("/actuator/war", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
