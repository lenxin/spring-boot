package org.springframework.boot.test.mock.web;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.ServletContextAware;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootMockServletContext}.
 *

 */
@DirtiesContext
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = SpringBootContextLoader.class)
@WebAppConfiguration("src/test/webapp")
class SpringBootMockServletContextTests implements ServletContextAware {

	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Test
	void getResourceLocation() throws Exception {
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

	// gh-2654
	@Test
	void getRootUrlExistsAndIsEmpty() throws Exception {
		SpringBootMockServletContext context = new SpringBootMockServletContext("src/test/doesntexist") {
			@Override
			protected String getResourceLocation(String path) {
				// Don't include the Spring Boot defaults for this test
				return getResourceBasePathLocation(path);
			}
		};
		URL resource = context.getResource("/");
		assertThat(resource).isNotNull();
		File file = new File(URLDecoder.decode(resource.getPath(), "UTF-8"));
		assertThat(file).exists().isDirectory();
		String[] contents = file.list((dir, name) -> !(".".equals(name) || "..".equals(name)));
		assertThat(contents).isNotNull();
		assertThat(contents).isEmpty();
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

	}

}
