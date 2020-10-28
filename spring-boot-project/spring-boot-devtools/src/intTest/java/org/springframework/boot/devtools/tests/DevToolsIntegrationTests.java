package org.springframework.boot.devtools.tests;

import java.io.File;
import java.io.IOException;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for DevTools.
 *

 */
class DevToolsIntegrationTests extends AbstractDevToolsIntegrationTests {

	private final TestRestTemplate template = new TestRestTemplate(
			new RestTemplateBuilder().requestFactory(() -> new HttpComponentsClientHttpRequestFactory(
					HttpClients.custom().setRetryHandler(new StandardHttpRequestRetryHandler(10, false)).build())));

	@ParameterizedTest(name = "{0}")
	@MethodSource("parameters")
	void addARequestMappingToAnExistingController(ApplicationLauncher applicationLauncher) throws Exception {
		launchApplication(applicationLauncher);
		String urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForEntity(urlBase + "/two", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
		controller("com.example.ControllerOne").withRequestMapping("one").withRequestMapping("two").build();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForObject(urlBase + "/two", String.class)).isEqualTo("two");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("parameters")
	void removeARequestMappingFromAnExistingController(ApplicationLauncher applicationLauncher) throws Exception {
		launchApplication(applicationLauncher);
		String urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		controller("com.example.ControllerOne").build();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForEntity(urlBase + "/one", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("parameters")
	void createAController(ApplicationLauncher applicationLauncher) throws Exception {
		launchApplication(applicationLauncher);
		String urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForEntity(urlBase + "/two", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
		controller("com.example.ControllerTwo").withRequestMapping("two").build();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForObject(urlBase + "/two", String.class)).isEqualTo("two");

	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("parameters")
	void createAControllerAndThenAddARequestMapping(ApplicationLauncher applicationLauncher) throws Exception {
		launchApplication(applicationLauncher);
		String urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForEntity(urlBase + "/two", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
		controller("com.example.ControllerTwo").withRequestMapping("two").build();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForObject(urlBase + "/two", String.class)).isEqualTo("two");
		controller("com.example.ControllerTwo").withRequestMapping("two").withRequestMapping("three").build();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/three", String.class)).isEqualTo("three");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("parameters")
	void createAControllerAndThenAddARequestMappingToAnExistingController(ApplicationLauncher applicationLauncher)
			throws Exception {
		launchApplication(applicationLauncher);
		String urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForEntity(urlBase + "/two", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
		controller("com.example.ControllerTwo").withRequestMapping("two").build();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForObject(urlBase + "/two", String.class)).isEqualTo("two");
		controller("com.example.ControllerOne").withRequestMapping("one").withRequestMapping("three").build();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForObject(urlBase + "/two", String.class)).isEqualTo("two");
		assertThat(this.template.getForObject(urlBase + "/three", String.class)).isEqualTo("three");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("parameters")
	void deleteAController(ApplicationLauncher applicationLauncher) throws Exception {
		launchApplication(applicationLauncher);
		String urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(new File(this.launchedApplication.getClassesDirectory(), "com/example/ControllerOne.class").delete())
				.isTrue();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForEntity(urlBase + "/one", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);

	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("parameters")
	void createAControllerAndThenDeleteIt(ApplicationLauncher applicationLauncher) throws Exception {
		launchApplication(applicationLauncher);
		String urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForEntity(urlBase + "/two", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
		controller("com.example.ControllerTwo").withRequestMapping("two").build();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForObject(urlBase + "/one", String.class)).isEqualTo("one");
		assertThat(this.template.getForObject(urlBase + "/two", String.class)).isEqualTo("two");
		assertThat(new File(this.launchedApplication.getClassesDirectory(), "com/example/ControllerTwo.class").delete())
				.isTrue();
		urlBase = "http://localhost:" + awaitServerPort();
		assertThat(this.template.getForEntity(urlBase + "/two", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
	}

	static Object[] parameters() throws IOException {
		Directories directories = new Directories(buildOutput, temp);
		return new Object[] { new Object[] { new LocalApplicationLauncher(directories) },
				new Object[] { new ExplodedRemoteApplicationLauncher(directories) },
				new Object[] { new JarFileRemoteApplicationLauncher(directories) } };
	}

}
