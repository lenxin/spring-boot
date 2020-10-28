package org.springframework.boot.autoconfigure.data.jpa;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.data.jpa.city.City;
import org.springframework.boot.autoconfigure.data.jpa.city.CityRepository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.Distance;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringDataWebAutoConfiguration} and
 * {@link JpaRepositoriesAutoConfiguration}.
 *


 */
class JpaWebAutoConfigurationTests {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(
					AutoConfigurations.of(DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
							JpaRepositoriesAutoConfiguration.class, SpringDataWebAutoConfiguration.class))
			.withPropertyValues("spring.datasource.generate-unique-name=true");

	@Test
	void springDataWebIsConfiguredWithJpaRepositories() {
		this.contextRunner.withUserConfiguration(TestConfiguration.class).run((context) -> {
			assertThat(context).hasSingleBean(CityRepository.class);
			assertThat(context).hasSingleBean(PageableHandlerMethodArgumentResolver.class);
			assertThat(context).hasSingleBean(SortHandlerMethodArgumentResolver.class);
			assertThat(context.getBean(FormattingConversionService.class).canConvert(String.class, Distance.class))
					.isTrue();
		});
	}

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(City.class)
	@EnableWebMvc
	static class TestConfiguration {

	}

}
