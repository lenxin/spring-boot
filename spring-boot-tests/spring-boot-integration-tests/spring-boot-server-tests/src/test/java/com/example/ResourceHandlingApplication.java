package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Test application for verifying an embedded container's static resource handling.
 *

 */
@SpringBootApplication
public class ResourceHandlingApplication {

	@Bean
	public ServletRegistrationBean<?> resourceServletRegistration() {
		ServletRegistrationBean<?> registration = new ServletRegistrationBean<HttpServlet>(new GetResourceServlet());
		registration.addUrlMappings("/servletContext");
		return registration;
	}

	@Bean
	public ServletRegistrationBean<?> resourcePathsServletRegistration() {
		ServletRegistrationBean<?> registration = new ServletRegistrationBean<HttpServlet>(
				new GetResourcePathsServlet());
		registration.addUrlMappings("/resourcePaths");
		return registration;
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(ResourceHandlingApplication.class).properties("server.port:0")
				.listeners(new WebServerPortFileWriter(args[0])).run(args);
	}

	private static final class GetResourcePathsServlet extends HttpServlet {

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			collectResourcePaths("/").forEach(resp.getWriter()::println);
			resp.getWriter().flush();
		}

		private Set<String> collectResourcePaths(String path) {
			Set<String> allResourcePaths = new LinkedHashSet<>();
			Set<String> pathsForPath = getServletContext().getResourcePaths(path);
			if (pathsForPath != null) {
				for (String resourcePath : pathsForPath) {
					allResourcePaths.add(resourcePath);
					allResourcePaths.addAll(collectResourcePaths(resourcePath));
				}
			}
			return allResourcePaths;
		}

	}

	private static final class GetResourceServlet extends HttpServlet {

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			URL resource = getServletContext().getResource(req.getQueryString());
			if (resource == null) {
				resp.sendError(404);
			}
			else {
				resp.getWriter().println(resource);
				resp.getWriter().flush();
			}
		}

	}

}
