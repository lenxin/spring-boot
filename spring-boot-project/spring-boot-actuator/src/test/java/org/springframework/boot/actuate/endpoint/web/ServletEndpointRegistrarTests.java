package org.springframework.boot.actuate.endpoint.web;

import java.util.Collections;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.actuate.endpoint.EndpointId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ServletEndpointRegistrar}.
 *


 */
@ExtendWith(MockitoExtension.class)
class ServletEndpointRegistrarTests {

	@Mock
	private ServletContext servletContext;

	@Mock
	private Dynamic dynamic;

	@Captor
	private ArgumentCaptor<Servlet> servlet;

	@Test
	void createWhenServletEndpointsIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ServletEndpointRegistrar(null, null))
				.withMessageContaining("ServletEndpoints must not be null");
	}

	@Test
	void onStartupShouldRegisterServlets() throws ServletException {
		assertBasePath(null, "/test/*");
	}

	@Test
	void onStartupWhenHasBasePathShouldIncludeBasePath() throws ServletException {
		assertBasePath("/actuator", "/actuator/test/*");
	}

	@Test
	void onStartupWhenHasEmptyBasePathShouldPrefixWithSlash() throws ServletException {
		assertBasePath("", "/test/*");
	}

	@Test
	void onStartupWhenHasRootBasePathShouldNotAddDuplicateSlash() throws ServletException {
		assertBasePath("/", "/test/*");
	}

	private void assertBasePath(String basePath, String expectedMapping) throws ServletException {
		given(this.servletContext.addServlet(any(String.class), any(Servlet.class))).willReturn(this.dynamic);
		ExposableServletEndpoint endpoint = mockEndpoint(new EndpointServlet(TestServlet.class));
		ServletEndpointRegistrar registrar = new ServletEndpointRegistrar(basePath, Collections.singleton(endpoint));
		registrar.onStartup(this.servletContext);
		verify(this.servletContext).addServlet(eq("test-actuator-endpoint"), this.servlet.capture());
		assertThat(this.servlet.getValue()).isInstanceOf(TestServlet.class);
		verify(this.dynamic).addMapping(expectedMapping);
	}

	@Test
	void onStartupWhenHasInitParametersShouldRegisterInitParameters() throws Exception {
		given(this.servletContext.addServlet(any(String.class), any(Servlet.class))).willReturn(this.dynamic);
		ExposableServletEndpoint endpoint = mockEndpoint(
				new EndpointServlet(TestServlet.class).withInitParameter("a", "b"));
		ServletEndpointRegistrar registrar = new ServletEndpointRegistrar("/actuator", Collections.singleton(endpoint));
		registrar.onStartup(this.servletContext);
		verify(this.dynamic).setInitParameters(Collections.singletonMap("a", "b"));
	}

	@Test
	void onStartupWhenHasLoadOnStartupShouldRegisterLoadOnStartup() throws Exception {
		given(this.servletContext.addServlet(any(String.class), any(Servlet.class))).willReturn(this.dynamic);
		ExposableServletEndpoint endpoint = mockEndpoint(new EndpointServlet(TestServlet.class).withLoadOnStartup(7));
		ServletEndpointRegistrar registrar = new ServletEndpointRegistrar("/actuator", Collections.singleton(endpoint));
		registrar.onStartup(this.servletContext);
		verify(this.dynamic).setLoadOnStartup(7);
	}

	@Test
	void onStartupWhenHasNotLoadOnStartupShouldRegisterDefaultValue() throws Exception {
		given(this.servletContext.addServlet(any(String.class), any(Servlet.class))).willReturn(this.dynamic);
		ExposableServletEndpoint endpoint = mockEndpoint(new EndpointServlet(TestServlet.class));
		ServletEndpointRegistrar registrar = new ServletEndpointRegistrar("/actuator", Collections.singleton(endpoint));
		registrar.onStartup(this.servletContext);
		verify(this.dynamic).setLoadOnStartup(-1);
	}

	private ExposableServletEndpoint mockEndpoint(EndpointServlet endpointServlet) {
		ExposableServletEndpoint endpoint = mock(ExposableServletEndpoint.class);
		given(endpoint.getEndpointId()).willReturn(EndpointId.of("test"));
		given(endpoint.getEndpointServlet()).willReturn(endpointServlet);
		given(endpoint.getRootPath()).willReturn("test");
		return endpoint;
	}

	static class TestServlet extends GenericServlet {

		@Override
		public void service(ServletRequest req, ServletResponse res) {
		}

	}

}
