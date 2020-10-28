package org.springframework.boot.test.autoconfigure.web.servlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcConfigurerAdapter;
import org.springframework.web.context.WebApplicationContext;

/**
 * Configuration for Spring Security's MockMvc integration.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(SecurityMockMvcRequestPostProcessors.class)
class MockMvcSecurityConfiguration {

	private static final String DEFAULT_SECURITY_FILTER_NAME = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;

	@Bean
	@ConditionalOnBean(name = DEFAULT_SECURITY_FILTER_NAME)
	SecurityMockMvcBuilderCustomizer securityMockMvcBuilderCustomizer() {
		return new SecurityMockMvcBuilderCustomizer();
	}

	/**
	 * {@link MockMvcBuilderCustomizer} that ensures that requests run with the user in
	 * the {@link TestSecurityContextHolder}.
	 *
	 * @see SecurityMockMvcRequestPostProcessors#testSecurityContext
	 */
	class SecurityMockMvcBuilderCustomizer implements MockMvcBuilderCustomizer {

		@Override
		public void customize(ConfigurableMockMvcBuilder<?> builder) {
			builder.apply(new MockMvcConfigurerAdapter() {

				@Override
				public RequestPostProcessor beforeMockMvcCreated(ConfigurableMockMvcBuilder<?> builder,
						WebApplicationContext context) {
					return SecurityMockMvcRequestPostProcessors.testSecurityContext();
				}

			});
		}

	}

}
