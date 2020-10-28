package org.springframework.boot.autoconfigure.context;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PropertyPlaceholderAutoConfiguration}.
 *


 */
class PropertyPlaceholderAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

	@Test
	void whenTheAutoConfigurationIsNotUsedThenBeanDefinitionPlaceholdersAreNotResolved() {
		this.contextRunner.withPropertyValues("fruit:banana").withInitializer(this::definePlaceholderBean)
				.run((context) -> assertThat(context.getBean(PlaceholderBean.class).fruit).isEqualTo("${fruit:apple}"));
	}

	@Test
	void whenTheAutoConfigurationIsUsedThenBeanDefinitionPlaceholdersAreResolved() {
		this.contextRunner.withPropertyValues("fruit:banana").withInitializer(this::definePlaceholderBean)
				.withConfiguration(AutoConfigurations.of(PropertyPlaceholderAutoConfiguration.class))
				.run((context) -> assertThat(context.getBean(PlaceholderBean.class).fruit).isEqualTo("banana"));
	}

	@Test
	void whenTheAutoConfigurationIsNotUsedThenValuePlaceholdersAreResolved() {
		this.contextRunner.withPropertyValues("fruit:banana").withUserConfiguration(PlaceholderConfig.class)
				.run((context) -> assertThat(context.getBean(PlaceholderConfig.class).fruit).isEqualTo("banana"));
	}

	@Test
	void whenTheAutoConfigurationIsUsedThenValuePlaceholdersAreResolved() {
		this.contextRunner.withPropertyValues("fruit:banana")
				.withConfiguration(AutoConfigurations.of(PropertyPlaceholderAutoConfiguration.class))
				.withUserConfiguration(PlaceholderConfig.class)
				.run((context) -> assertThat(context.getBean(PlaceholderConfig.class).fruit).isEqualTo("banana"));
	}

	@Test
	void whenThereIsAUserDefinedPropertySourcesPlaceholderConfigurerThenItIsUsedForBeanDefinitionPlaceholderResolution() {
		this.contextRunner.withPropertyValues("fruit:banana").withInitializer(this::definePlaceholderBean)
				.withConfiguration(AutoConfigurations.of(PropertyPlaceholderAutoConfiguration.class))
				.withUserConfiguration(PlaceholdersOverride.class)
				.run((context) -> assertThat(context.getBean(PlaceholderBean.class).fruit).isEqualTo("orange"));
	}

	@Test
	void whenThereIsAUserDefinedPropertySourcesPlaceholderConfigurerThenItIsUsedForValuePlaceholderResolution() {
		this.contextRunner.withPropertyValues("fruit:banana")
				.withConfiguration(AutoConfigurations.of(PropertyPlaceholderAutoConfiguration.class))
				.withUserConfiguration(PlaceholderConfig.class, PlaceholdersOverride.class)
				.run((context) -> assertThat(context.getBean(PlaceholderConfig.class).fruit).isEqualTo("orange"));
	}

	private void definePlaceholderBean(ConfigurableApplicationContext context) {
		((BeanDefinitionRegistry) context.getBeanFactory()).registerBeanDefinition("placeholderBean",
				BeanDefinitionBuilder.genericBeanDefinition(PlaceholderBean.class)
						.addConstructorArgValue("${fruit:apple}").getBeanDefinition());
	}

	@Configuration(proxyBeanMethods = false)
	static class PlaceholderConfig {

		@Value("${fruit:apple}")
		private String fruit;

	}

	static class PlaceholderBean {

		private final String fruit;

		PlaceholderBean(String fruit) {
			this.fruit = fruit;
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class PlaceholdersOverride {

		@Bean
		static PropertySourcesPlaceholderConfigurer morePlaceholders() {
			PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
			configurer
					.setProperties(StringUtils.splitArrayElementsIntoProperties(new String[] { "fruit=orange" }, "="));
			configurer.setLocalOverride(true);
			configurer.setOrder(0);
			return configurer;
		}

	}

}
