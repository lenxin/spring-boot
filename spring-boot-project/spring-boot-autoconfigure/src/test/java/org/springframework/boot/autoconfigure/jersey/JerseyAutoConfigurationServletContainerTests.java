package org.springframework.boot.autoconfigure.jersey;

import java.nio.charset.StandardCharsets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.tomcat.util.buf.UDecoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfigurationServletContainerTests.Application;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that verify the behavior when deployed to a Servlet container where Jersey may
 * have already initialized itself.
 *

 */
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ExtendWith(OutputCaptureExtension.class)
class JerseyAutoConfigurationServletContainerTests {

	@Test
	void existingJerseyServletIsAmended(CapturedOutput output) {
		assertThat(output).contains("Configuring existing registration for Jersey servlet");
		assertThat(output).contains("Servlet " + Application.class.getName() + " was not registered");
	}

	@ImportAutoConfiguration({ ServletWebServerFactoryAutoConfiguration.class, JerseyAutoConfiguration.class,
			PropertyPlaceholderAutoConfiguration.class })
	@Import(ContainerConfiguration.class)
	@Path("/hello")
	@Configuration(proxyBeanMethods = false)
	public static class Application extends ResourceConfig {

		@Value("${message:World}")
		private String msg;

		Application() {
			register(Application.class);
		}

		@GET
		public String message() {
			return "Hello " + this.msg;
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class ContainerConfiguration {

		@Bean
		TomcatServletWebServerFactory tomcat() {
			return new TomcatServletWebServerFactory() {

				@Override
				protected void postProcessContext(Context context) {
					Wrapper jerseyServlet = context.createWrapper();
					String servletName = Application.class.getName();
					jerseyServlet.setName(servletName);
					jerseyServlet.setServletClass(ServletContainer.class.getName());
					jerseyServlet.setServlet(new ServletContainer());
					jerseyServlet.setOverridable(false);
					context.addChild(jerseyServlet);
					String pattern = UDecoder.URLDecode("/*", StandardCharsets.UTF_8);
					context.addServletMappingDecoded(pattern, servletName);
				}

			};
		}

	}

}
