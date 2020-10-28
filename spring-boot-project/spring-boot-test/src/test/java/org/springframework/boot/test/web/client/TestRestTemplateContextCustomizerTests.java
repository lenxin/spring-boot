package org.springframework.boot.test.web.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.MergedContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link TestRestTemplateContextCustomizer}.
 *

 */
class TestRestTemplateContextCustomizerTests {

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void whenContextIsNotABeanDefinitionRegistryTestRestTemplateIsRegistered() {
		new ApplicationContextRunner(TestApplicationContext::new).withInitializer((context) -> {
			MergedContextConfiguration configuration = mock(MergedContextConfiguration.class);
			given(configuration.getTestClass()).willReturn((Class) TestClass.class);
			new TestRestTemplateContextCustomizer().customizeContext(context, configuration);
		}).run((context) -> assertThat(context).hasSingleBean(TestRestTemplate.class));
	}

	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	static class TestClass {

	}

	static class TestApplicationContext extends AbstractApplicationContext {

		private final ConfigurableListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		@Override
		protected void refreshBeanFactory() throws BeansException, IllegalStateException {
		}

		@Override
		protected void closeBeanFactory() {

		}

		@Override
		public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
			return this.beanFactory;
		}

	}

}
