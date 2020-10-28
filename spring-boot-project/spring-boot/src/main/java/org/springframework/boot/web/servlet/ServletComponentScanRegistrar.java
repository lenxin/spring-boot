package org.springframework.boot.web.servlet;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * {@link ImportBeanDefinitionRegistrar} used by
 * {@link ServletComponentScan @ServletComponentScan}.
 *


 */
class ServletComponentScanRegistrar implements ImportBeanDefinitionRegistrar {

	private static final String BEAN_NAME = "servletComponentRegisteringPostProcessor";

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);
		if (registry.containsBeanDefinition(BEAN_NAME)) {
			updatePostProcessor(registry, packagesToScan);
		}
		else {
			addPostProcessor(registry, packagesToScan);
		}
	}

	private void updatePostProcessor(BeanDefinitionRegistry registry, Set<String> packagesToScan) {
		ServletComponentRegisteringPostProcessorBeanDefinition definition = (ServletComponentRegisteringPostProcessorBeanDefinition) registry
				.getBeanDefinition(BEAN_NAME);
		definition.addPackageNames(packagesToScan);
	}

	private void addPostProcessor(BeanDefinitionRegistry registry, Set<String> packagesToScan) {
		ServletComponentRegisteringPostProcessorBeanDefinition definition = new ServletComponentRegisteringPostProcessorBeanDefinition(
				packagesToScan);
		registry.registerBeanDefinition(BEAN_NAME, definition);
	}

	private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
		AnnotationAttributes attributes = AnnotationAttributes
				.fromMap(metadata.getAnnotationAttributes(ServletComponentScan.class.getName()));
		String[] basePackages = attributes.getStringArray("basePackages");
		Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
		Set<String> packagesToScan = new LinkedHashSet<>(Arrays.asList(basePackages));
		for (Class<?> basePackageClass : basePackageClasses) {
			packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
		}
		if (packagesToScan.isEmpty()) {
			packagesToScan.add(ClassUtils.getPackageName(metadata.getClassName()));
		}
		return packagesToScan;
	}

	static final class ServletComponentRegisteringPostProcessorBeanDefinition extends GenericBeanDefinition {

		private Set<String> packageNames = new LinkedHashSet<>();

		ServletComponentRegisteringPostProcessorBeanDefinition(Collection<String> packageNames) {
			setBeanClass(ServletComponentRegisteringPostProcessor.class);
			setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			addPackageNames(packageNames);
		}

		@Override
		public Supplier<?> getInstanceSupplier() {
			return () -> new ServletComponentRegisteringPostProcessor(this.packageNames);
		}

		private void addPackageNames(Collection<String> additionalPackageNames) {
			this.packageNames.addAll(additionalPackageNames);
		}

	}

}
