package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} when loading resources via
 * {@link ServletContext}.
 *

 */
@WebMvcTest
class WebMvcTestServletContextResourceTests {

	@Autowired
	private ServletContext servletContext;

	@Test
	void getResourceLocation() throws Exception {
		testResource("/inwebapp", "src/main/webapp");
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
