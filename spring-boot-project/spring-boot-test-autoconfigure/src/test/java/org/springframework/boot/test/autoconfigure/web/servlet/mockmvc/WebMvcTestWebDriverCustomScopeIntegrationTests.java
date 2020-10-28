package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} with {@link WebDriver} in a custom scope.
 *

 */
@WebMvcTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class WebMvcTestWebDriverCustomScopeIntegrationTests {

	// gh-7454

	private static WebDriver previousWebDriver;

	@Autowired
	private WebDriver webDriver;

	@Test
	void shouldAutoConfigureWebClient() {
		WebMvcTestWebDriverCustomScopeIntegrationTests.previousWebDriver = this.webDriver;
	}

	@Test
	void shouldBeTheSameWebClient() {
		assertThat(previousWebDriver).isNotNull().isSameAs(this.webDriver);
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

		@Bean
		@Scope("singleton")
		WebDriverFactory webDriver(MockMvc mockMvc) {
			return new WebDriverFactory(mockMvc);
		}

	}

	static class WebDriverFactory implements FactoryBean<WebDriver> {

		private final HtmlUnitDriver driver;

		WebDriverFactory(MockMvc mockMvc) {
			this.driver = MockMvcHtmlUnitDriverBuilder.mockMvcSetup(mockMvc).build();
		}

		@Override
		public boolean isSingleton() {
			return true;
		}

		@Override
		public Class<?> getObjectType() {
			return WebDriver.class;
		}

		@Override
		public WebDriver getObject() {
			return this.driver;
		}

	}

}
