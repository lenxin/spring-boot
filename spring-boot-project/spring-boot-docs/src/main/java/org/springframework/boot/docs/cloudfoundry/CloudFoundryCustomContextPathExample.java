package org.springframework.boot.docs.cloudfoundry;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.catalina.Host;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Example configuration for custom context path in Cloud Foundry.
 *

 */
@Configuration(proxyBeanMethods = false)
public class CloudFoundryCustomContextPathExample {

	// tag::configuration[]
	@Bean
	public TomcatServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory() {

			@Override
			protected void prepareContext(Host host, ServletContextInitializer[] initializers) {
				super.prepareContext(host, initializers);
				StandardContext child = new StandardContext();
				child.addLifecycleListener(new Tomcat.FixContextListener());
				child.setPath("/cloudfoundryapplication");
				ServletContainerInitializer initializer = getServletContextInitializer(getContextPath());
				child.addServletContainerInitializer(initializer, Collections.emptySet());
				child.setCrossContext(true);
				host.addChild(child);
			}

		};
	}

	private ServletContainerInitializer getServletContextInitializer(String contextPath) {
		return (c, context) -> {
			Servlet servlet = new GenericServlet() {

				@Override
				public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
					ServletContext context = req.getServletContext().getContext(contextPath);
					context.getRequestDispatcher("/cloudfoundryapplication").forward(req, res);
				}

			};
			context.addServlet("cloudfoundry", servlet).addMapping("/*");
		};
	}
	// end::configuration[]

}
