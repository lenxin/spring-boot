package org.springframework.boot.autoconfigure;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AbstractDependsOnBeanFactoryPostProcessor}.
 *

 */
class AbstractDependsOnBeanFactoryPostProcessorTests {

	private ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(FooBarConfiguration.class);

	@Test
	void fooBeansShouldDependOnBarBeanNames() {
		this.contextRunner
				.withUserConfiguration(FooDependsOnBarNamePostProcessor.class, FooBarFactoryBeanConfiguration.class)
				.run(this::assertThatFooDependsOnBar);
	}

	@Test
	void fooBeansShouldDependOnBarBeanTypes() {
		this.contextRunner
				.withUserConfiguration(FooDependsOnBarTypePostProcessor.class, FooBarFactoryBeanConfiguration.class)
				.run(this::assertThatFooDependsOnBar);
	}

	@Test
	void fooBeansShouldDependOnBarBeanNamesParentContext() {
		try (AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext(
				FooBarFactoryBeanConfiguration.class)) {
			this.contextRunner.withUserConfiguration(FooDependsOnBarNamePostProcessor.class).withParent(parentContext)
					.run(this::assertThatFooDependsOnBar);
		}
	}

	@Test
	void fooBeansShouldDependOnBarBeanTypesParentContext() {
		try (AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext(
				FooBarFactoryBeanConfiguration.class)) {
			this.contextRunner.withUserConfiguration(FooDependsOnBarTypePostProcessor.class).withParent(parentContext)
					.run(this::assertThatFooDependsOnBar);
		}
	}

	@Test
	void postProcessorHasADefaultOrderOfZero() {
		assertThat(new FooDependsOnBarTypePostProcessor().getOrder()).isEqualTo(0);
	}

	private void assertThatFooDependsOnBar(AssertableApplicationContext context) {
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		assertThat(getBeanDefinition("foo", beanFactory).getDependsOn()).containsExactly("bar", "barFactoryBean");
		assertThat(getBeanDefinition("fooFactoryBean", beanFactory).getDependsOn()).containsExactly("bar",
				"barFactoryBean");
	}

	private BeanDefinition getBeanDefinition(String beanName, ConfigurableListableBeanFactory beanFactory) {
		try {
			return beanFactory.getBeanDefinition(beanName);
		}
		catch (NoSuchBeanDefinitionException ex) {
			BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
			if (parentBeanFactory instanceof ConfigurableListableBeanFactory) {
				return getBeanDefinition(beanName, (ConfigurableListableBeanFactory) parentBeanFactory);
			}
			throw ex;
		}
	}

	static class Foo {

	}

	static class Bar {

	}

	@Configuration(proxyBeanMethods = false)
	static class FooBarFactoryBeanConfiguration {

		@Bean
		FooFactoryBean fooFactoryBean() {
			return new FooFactoryBean();
		}

		@Bean
		BarFactoryBean barFactoryBean() {
			return new BarFactoryBean();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class FooBarConfiguration {

		@Bean
		Bar bar() {
			return new Bar();
		}

		@Bean
		Foo foo() {
			return new Foo();
		}

	}

	static class FooDependsOnBarTypePostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {

		protected FooDependsOnBarTypePostProcessor() {
			super(Foo.class, FooFactoryBean.class, Bar.class, BarFactoryBean.class);
		}

	}

	static class FooDependsOnBarNamePostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {

		protected FooDependsOnBarNamePostProcessor() {
			super(Foo.class, FooFactoryBean.class, "bar", "barFactoryBean");
		}

	}

	static class FooFactoryBean implements FactoryBean<Foo> {

		@Override
		public Foo getObject() {
			return new Foo();
		}

		@Override
		public Class<?> getObjectType() {
			return Foo.class;
		}

	}

	static class BarFactoryBean implements FactoryBean<Bar> {

		@Override
		public Bar getObject() {
			return new Bar();
		}

		@Override
		public Class<?> getObjectType() {
			return Bar.class;
		}

	}

}
