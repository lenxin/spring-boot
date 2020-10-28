package org.springframework.boot.test.autoconfigure.web.servlet;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.AutoConfigurationImportedCondition.importedAutoConfiguration;

/**
 * Tests for the auto-configuration imported by {@link WebMvcTest @WebMvcTest}.
 *



 */
@WebMvcTest
class WebMvcTestAutoConfigurationIntegrationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void freemarkerAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(FreeMarkerAutoConfiguration.class));
	}

	@Test
	void groovyTemplatesAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(GroovyTemplateAutoConfiguration.class));
	}

	@Test
	void mustacheAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(MustacheAutoConfiguration.class));
	}

	@Test
	void thymeleafAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(ThymeleafAutoConfiguration.class));
	}

	@Test
	void taskExecutionAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(TaskExecutionAutoConfiguration.class));
	}

	@Test
	void asyncTaskExecutorWithApplicationTaskExecutor() {
		assertThat(this.applicationContext.getBeansOfType(AsyncTaskExecutor.class)).hasSize(1);
		assertThat(this.applicationContext.getBean(RequestMappingHandlerAdapter.class)).extracting("taskExecutor")
				.isSameAs(this.applicationContext.getBean("applicationTaskExecutor"));
	}

	@Test
	void oAuth2ClientAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(OAuth2ClientAutoConfiguration.class));
	}

	@Test
	void oAuth2ResourceServerAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(OAuth2ResourceServerAutoConfiguration.class));
	}

	@Test
	void httpEncodingAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(HttpEncodingAutoConfiguration.class));
	}

}
