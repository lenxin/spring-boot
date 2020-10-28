package org.springframework.boot.context.properties;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link ConfigurationPropertiesBeanRegistrar}.
 *



 */
class ConfigurationPropertiesBeanRegistrarTests {

	private BeanDefinitionRegistry registry = new DefaultListableBeanFactory();

	private ConfigurationPropertiesBeanRegistrar registrar = new ConfigurationPropertiesBeanRegistrar(this.registry);

	@Test
	void registerWhenNotAlreadyRegisteredAddBeanDefinition() {
		String beanName = "beancp-" + BeanConfigurationProperties.class.getName();
		this.registrar.register(BeanConfigurationProperties.class);
		BeanDefinition definition = this.registry.getBeanDefinition(beanName);
		assertThat(definition).isNotNull();
		assertThat(definition.getBeanClassName()).isEqualTo(BeanConfigurationProperties.class.getName());
	}

	@Test
	void registerWhenAlreadyContainsNameDoesNotReplace() {
		String beanName = "beancp-" + BeanConfigurationProperties.class.getName();
		this.registry.registerBeanDefinition(beanName, new GenericBeanDefinition());
		this.registrar.register(BeanConfigurationProperties.class);
		BeanDefinition definition = this.registry.getBeanDefinition(beanName);
		assertThat(definition).isNotNull();
		assertThat(definition.getBeanClassName()).isNull();
	}

	@Test
	void registerWhenNoAnnotationThrowsException() {
		assertThatIllegalStateException()
				.isThrownBy(() -> this.registrar.register(NoAnnotationConfigurationProperties.class))
				.withMessageContaining("No ConfigurationProperties annotation found");
	}

	@Test
	void registerWhenValueObjectRegistersValueObjectBeanDefinition() {
		String beanName = "valuecp-" + ValueObjectConfigurationProperties.class.getName();
		this.registrar.register(ValueObjectConfigurationProperties.class);
		BeanDefinition definition = this.registry.getBeanDefinition(beanName);
		assertThat(definition).isInstanceOf(ConfigurationPropertiesValueObjectBeanDefinition.class);
	}

	@Test
	void registerWhenNotValueObjectRegistersGenericBeanDefinition() {
		String beanName = MultiConstructorBeanConfigurationProperties.class.getName();
		this.registrar.register(MultiConstructorBeanConfigurationProperties.class);
		BeanDefinition definition = this.registry.getBeanDefinition(beanName);
		assertThat(definition).isInstanceOf(GenericBeanDefinition.class);
	}

	@ConfigurationProperties(prefix = "beancp")
	static class BeanConfigurationProperties {

	}

	static class NoAnnotationConfigurationProperties {

	}

	@ConstructorBinding
	@ConfigurationProperties("valuecp")
	static class ValueObjectConfigurationProperties {

		ValueObjectConfigurationProperties(String name) {
		}

	}

	@ConfigurationProperties
	static class MultiConstructorBeanConfigurationProperties {

		MultiConstructorBeanConfigurationProperties() {
		}

		MultiConstructorBeanConfigurationProperties(String name) {
		}

	}

}
