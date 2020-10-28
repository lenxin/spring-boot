package org.springframework.boot.test.autoconfigure.web.servlet;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.DispatcherServletCustomizer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Auto-configuration for {@link MockMvc}.
 *



 * @see AutoConfigureWebMvc
 * @since 1.4.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties({ ServerProperties.class, WebMvcProperties.class })
public class MockMvcAutoConfiguration {

	private final WebApplicationContext context;

	private final WebMvcProperties webMvcProperties;

	MockMvcAutoConfiguration(WebApplicationContext context, WebMvcProperties webMvcProperties) {
		this.context = context;
		this.webMvcProperties = webMvcProperties;
	}

	@Bean
	@ConditionalOnMissingBean
	public DispatcherServletPath dispatcherServletPath() {
		return () -> this.webMvcProperties.getServlet().getPath();
	}

	@Bean
	@ConditionalOnMissingBean(MockMvcBuilder.class)
	public DefaultMockMvcBuilder mockMvcBuilder(List<MockMvcBuilderCustomizer> customizers) {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.context);
		builder.addDispatcherServletCustomizer(new MockMvcDispatcherServletCustomizer(this.webMvcProperties));
		for (MockMvcBuilderCustomizer customizer : customizers) {
			customizer.customize(builder);
		}
		return builder;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.test.mockmvc")
	public SpringBootMockMvcBuilderCustomizer springBootMockMvcBuilderCustomizer() {
		return new SpringBootMockMvcBuilderCustomizer(this.context);
	}

	@Bean
	@ConditionalOnMissingBean
	public MockMvc mockMvc(MockMvcBuilder builder) {
		return builder.build();
	}

	@Bean
	@ConditionalOnMissingBean
	public DispatcherServlet dispatcherServlet(MockMvc mockMvc) {
		return mockMvc.getDispatcherServlet();
	}

	private static class MockMvcDispatcherServletCustomizer implements DispatcherServletCustomizer {

		private final WebMvcProperties webMvcProperties;

		MockMvcDispatcherServletCustomizer(WebMvcProperties webMvcProperties) {
			this.webMvcProperties = webMvcProperties;
		}

		@Override
		public void customize(DispatcherServlet dispatcherServlet) {
			dispatcherServlet.setDispatchOptionsRequest(this.webMvcProperties.isDispatchOptionsRequest());
			dispatcherServlet.setDispatchTraceRequest(this.webMvcProperties.isDispatchTraceRequest());
			dispatcherServlet
					.setThrowExceptionIfNoHandlerFound(this.webMvcProperties.isThrowExceptionIfNoHandlerFound());
		}

	}

}
