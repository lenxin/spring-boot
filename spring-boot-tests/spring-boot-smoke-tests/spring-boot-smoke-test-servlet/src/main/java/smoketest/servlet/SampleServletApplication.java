package smoketest.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
@EnableAutoConfiguration
public class SampleServletApplication extends SpringBootServletInitializer {

	@SuppressWarnings("serial")
	@Bean
	public Servlet dispatcherServlet() {
		return new GenericServlet() {
			@Override
			public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
				res.setContentType("text/plain");
				res.getWriter().append("Hello World");
			}
		};
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SampleServletApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleServletApplication.class, args);
	}

}
