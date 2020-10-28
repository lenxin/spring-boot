package org.springframework.boot.actuate.beans;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.actuate.beans.BeansEndpoint.ApplicationBeans;
import org.springframework.boot.actuate.beans.BeansEndpoint.BeanDescriptor;
import org.springframework.boot.actuate.beans.BeansEndpoint.ContextBeans;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BeansEndpoint}.
 *


 */
class BeansEndpointTests {

	@Test
	void beansAreFound() {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withUserConfiguration(EndpointConfiguration.class);
		contextRunner.run((context) -> {
			ApplicationBeans result = context.getBean(BeansEndpoint.class).beans();
			ContextBeans descriptor = result.getContexts().get(context.getId());
			assertThat(descriptor.getParentId()).isNull();
			Map<String, BeanDescriptor> beans = descriptor.getBeans();
			assertThat(beans.size()).isLessThanOrEqualTo(context.getBeanDefinitionCount());
			assertThat(beans).containsKey("endpoint");
		});
	}

	@Test
	void infrastructureBeansAreOmitted() {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withUserConfiguration(EndpointConfiguration.class);
		contextRunner.run((context) -> {
			ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) context
					.getAutowireCapableBeanFactory();
			List<String> infrastructureBeans = Stream.of(context.getBeanDefinitionNames())
					.filter((name) -> BeanDefinition.ROLE_INFRASTRUCTURE == factory.getBeanDefinition(name).getRole())
					.collect(Collectors.toList());
			ApplicationBeans result = context.getBean(BeansEndpoint.class).beans();
			ContextBeans contextDescriptor = result.getContexts().get(context.getId());
			Map<String, BeanDescriptor> beans = contextDescriptor.getBeans();
			for (String infrastructureBean : infrastructureBeans) {
				assertThat(beans).doesNotContainKey(infrastructureBean);
			}
		});
	}

	@Test
	void lazyBeansAreOmitted() {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withUserConfiguration(EndpointConfiguration.class, LazyBeanConfiguration.class);
		contextRunner.run((context) -> {
			ApplicationBeans result = context.getBean(BeansEndpoint.class).beans();
			ContextBeans contextDescriptor = result.getContexts().get(context.getId());
			assertThat(context).hasBean("lazyBean");
			assertThat(contextDescriptor.getBeans()).doesNotContainKey("lazyBean");
		});
	}

	@Test
	void beansInParentContextAreFound() {
		ApplicationContextRunner parentRunner = new ApplicationContextRunner()
				.withUserConfiguration(BeanConfiguration.class);
		parentRunner.run((parent) -> {
			new ApplicationContextRunner().withUserConfiguration(EndpointConfiguration.class).withParent(parent)
					.run((child) -> {
						ApplicationBeans result = child.getBean(BeansEndpoint.class).beans();
						assertThat(result.getContexts().get(parent.getId()).getBeans()).containsKey("bean");
						assertThat(result.getContexts().get(child.getId()).getBeans()).containsKey("endpoint");
					});
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class EndpointConfiguration {

		@Bean
		BeansEndpoint endpoint(ConfigurableApplicationContext context) {
			return new BeansEndpoint(context);
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class BeanConfiguration {

		@Bean
		String bean() {
			return "bean";
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class LazyBeanConfiguration {

		@Lazy
		@Bean
		String lazyBean() {
			return "lazyBean";
		}

	}

}
