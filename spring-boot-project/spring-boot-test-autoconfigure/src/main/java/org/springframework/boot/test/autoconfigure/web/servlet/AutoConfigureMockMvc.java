package org.springframework.boot.test.autoconfigure.web.servlet;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.WebDriver;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.autoconfigure.properties.SkipPropertyMapping;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Annotation that can be applied to a test class to enable and configure
 * auto-configuration of {@link MockMvc}.
 *

 * @since 1.4.0
 * @see MockMvcAutoConfiguration
 * @see SpringBootMockMvcBuilderCustomizer
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
@PropertyMapping("spring.test.mockmvc")
public @interface AutoConfigureMockMvc {

	/**
	 * If filters from the application context should be registered with MockMVC. Defaults
	 * to {@code true}.
	 * @return if filters should be added
	 */
	boolean addFilters() default true;

	/**
	 * How {@link MvcResult} information should be printed after each MockMVC invocation.
	 * @return how information is printed
	 */
	@PropertyMapping(skip = SkipPropertyMapping.ON_DEFAULT_VALUE)
	MockMvcPrint print() default MockMvcPrint.DEFAULT;

	/**
	 * If {@link MvcResult} information should be printed only if the test fails.
	 * @return {@code true} if printing only occurs on failure
	 */
	boolean printOnlyOnFailure() default true;

	/**
	 * If a {@link WebClient} should be auto-configured when HtmlUnit is on the classpath.
	 * Defaults to {@code true}.
	 * @return if a {@link WebClient} is auto-configured
	 */
	@PropertyMapping("webclient.enabled")
	boolean webClientEnabled() default true;

	/**
	 * If a {@link WebDriver} should be auto-configured when Selenium is on the classpath.
	 * Defaults to {@code true}.
	 * @return if a {@link WebDriver} is auto-configured
	 */
	@PropertyMapping("webdriver.enabled")
	boolean webDriverEnabled() default true;

}
