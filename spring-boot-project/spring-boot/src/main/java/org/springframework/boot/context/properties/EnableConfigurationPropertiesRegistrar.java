package org.springframework.boot.context.properties;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

/**
 * {@link ImportBeanDefinitionRegistrar} for
 * {@link EnableConfigurationProperties @EnableConfigurationProperties}.
 *

 */
class EnableConfigurationPropertiesRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		registerInfrastructureBeans(registry);
		ConfigurationPropertiesBeanRegistrar beanRegistrar = new ConfigurationPropertiesBeanRegistrar(registry);
		getTypes(metadata).forEach(beanRegistrar::register);
	}

	private Set<Class<?>> getTypes(AnnotationMetadata metadata) {
		return metadata.getAnnotations().stream(EnableConfigurationProperties.class)
				.flatMap((annotation) -> Arrays.stream(annotation.getClassArray(MergedAnnotation.VALUE)))
				.filter((type) -> void.class != type).collect(Collectors.toSet());
	}

	static void registerInfrastructureBeans(BeanDefinitionRegistry registry) {
		ConfigurationPropertiesBindingPostProcessor.register(registry);
		BoundConfigurationProperties.register(registry);
	}

}
