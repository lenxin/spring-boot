package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link WebMvcTest @WebMvcTest} with {@link WebDriver}.
 *

 */
@WebMvcTest
@WithMockUser
@TestMethodOrder(MethodOrderer.MethodName.class)
class WebMvcTestWebDriverIntegrationTests {

	private static WebDriver previousWebDriver;

	@Autowired
	private WebDriver webDriver;

	@Test
	void shouldAutoConfigureWebClient() {
		this.webDriver.get("/html");
		WebElement element = this.webDriver.findElement(By.tagName("body"));
		assertThat(element.getText()).isEqualTo("Hello");
		WebMvcTestWebDriverIntegrationTests.previousWebDriver = this.webDriver;
	}

	@Test
	void shouldBeADifferentWebClient() {
		this.webDriver.get("/html");
		WebElement element = this.webDriver.findElement(By.tagName("body"));
		assertThat(element.getText()).isEqualTo("Hello");
		assertThatExceptionOfType(NoSuchWindowException.class).isThrownBy(previousWebDriver::getWindowHandle);
		assertThat(previousWebDriver).isNotNull().isNotSameAs(this.webDriver);
	}

}
