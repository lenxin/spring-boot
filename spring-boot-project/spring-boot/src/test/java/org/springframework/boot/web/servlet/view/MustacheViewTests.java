package org.springframework.boot.web.servlet.view;

import java.util.Collections;

import com.samskivert.mustache.Mustache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MustacheView}.
 *

 */
class MustacheViewTests {

	private final String templateUrl = "classpath:/" + getClass().getPackage().getName().replace(".", "/")
			+ "/template.html";

	private MockHttpServletRequest request = new MockHttpServletRequest();

	private MockHttpServletResponse response = new MockHttpServletResponse();

	private AnnotationConfigServletWebApplicationContext context = new AnnotationConfigServletWebApplicationContext();

	@BeforeEach
	void init() {
		this.context.refresh();
		MockServletContext servletContext = new MockServletContext();
		this.context.setServletContext(servletContext);
		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
	}

	@Test
	void viewResolvesHandlebars() throws Exception {
		MustacheView view = new MustacheView();
		view.setCompiler(Mustache.compiler());
		view.setUrl(this.templateUrl);
		view.setApplicationContext(this.context);
		view.render(Collections.singletonMap("World", "Spring"), this.request, this.response);
		assertThat(this.response.getContentAsString().trim()).isEqualTo("Hello Spring");
	}

}
