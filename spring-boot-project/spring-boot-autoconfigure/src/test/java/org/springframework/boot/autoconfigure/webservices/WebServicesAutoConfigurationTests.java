package org.springframework.boot.autoconfigure.webservices;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebServicesAutoConfiguration}.
 *




 */
class WebServicesAutoConfigurationTests {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(WebServicesAutoConfiguration.class));

	@Test
	void defaultConfiguration() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(ServletRegistrationBean.class));
	}

	@Test
	void customPathMustBeginWithASlash() {
		this.contextRunner.withPropertyValues("spring.webservices.path=invalid")
				.run((context) -> assertThat(context).getFailure().isInstanceOf(BeanCreationException.class)
						.hasMessageContaining("Failed to bind properties under 'spring.webservices'"));
	}

	@Test
	void customPath() {
		this.contextRunner.withPropertyValues("spring.webservices.path=/valid")
				.run((context) -> assertThat(getUrlMappings(context)).contains("/valid/*"));
	}

	@Test
	void customPathWithTrailingSlash() {
		this.contextRunner.withPropertyValues("spring.webservices.path=/valid/")
				.run((context) -> assertThat(getUrlMappings(context)).contains("/valid/*"));
	}

	@Test
	void customLoadOnStartup() {
		this.contextRunner.withPropertyValues("spring.webservices.servlet.load-on-startup=1").run((context) -> {
			ServletRegistrationBean<?> registrationBean = context.getBean(ServletRegistrationBean.class);
			assertThat(registrationBean).extracting("loadOnStartup").isEqualTo(1);
		});
	}

	@Test
	void customInitParameters() {
		this.contextRunner
				.withPropertyValues("spring.webservices.servlet.init.key1=value1",
						"spring.webservices.servlet.init.key2=value2")
				.run((context) -> assertThat(getServletRegistrationBean(context).getInitParameters())
						.containsEntry("key1", "value1").containsEntry("key2", "value2"));
	}

	@Test
	void withWsdlBeans() {
		this.contextRunner.withPropertyValues("spring.webservices.wsdl-locations=classpath:/wsdl").run((context) -> {
			assertThat(context.getBeansOfType(SimpleWsdl11Definition.class)).containsOnlyKeys("service");
			assertThat(context.getBeansOfType(SimpleXsdSchema.class)).containsOnlyKeys("types");
		});
	}

	@Test
	void withWsdlBeansAsList() {
		this.contextRunner.withPropertyValues("spring.webservices.wsdl-locations[0]=classpath:/wsdl").run((context) -> {
			assertThat(context.getBeansOfType(SimpleWsdl11Definition.class)).containsOnlyKeys("service");
			assertThat(context.getBeansOfType(SimpleXsdSchema.class)).containsOnlyKeys("types");
		});
	}

	private Collection<String> getUrlMappings(ApplicationContext context) {
		return getServletRegistrationBean(context).getUrlMappings();
	}

	private ServletRegistrationBean<?> getServletRegistrationBean(ApplicationContext loaded) {
		return loaded.getBean(ServletRegistrationBean.class);
	}

}
