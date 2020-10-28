package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} when loading resources via the
 * {@link ServletContext} with {@link WebAppConfiguration @WebAppConfiguration}.
 *

 */
@WebMvcTest
@WebAppConfiguration("src/test/webapp")
class WebMvcTestWithWebAppConfigurationTests {

	@Autowired
	private ServletContext servletContext;

	@Test
	void whenBasePathIsCustomizedResourcesCanBeLoadedFromThatLocation() throws Exception {
		testResource("/inwebapp", "src/test/webapp");
		testResource("/inmetainfresources", "/META-INF/resources");
		testResource("/inresources", "/resources");
		testResource("/instatic", "/static");
		testResource("/inpublic", "/public");
	}

	private void testResource(String path, String expectedLocation) throws MalformedURLException {
		URL resource = this.servletContext.getResource(path);
		assertThat(resource).isNotNull();
		assertThat(resource.getPath()).contains(expectedLocation);
	}

}
