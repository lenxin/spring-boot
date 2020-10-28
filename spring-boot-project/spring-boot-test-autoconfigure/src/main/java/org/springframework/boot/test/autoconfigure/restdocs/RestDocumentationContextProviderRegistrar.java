package org.springframework.boot.test.autoconfigure.restdocs;

import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.util.StringUtils;

/**
 * {@link ImportBeanDefinitionRegistrar} used by
 * {@link AutoConfigureRestDocs @AutoConfigureRestDocs}.
 *

 * @see AutoConfigureRestDocs
 */
class RestDocumentationContextProviderRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Object> annotationAttributes = importingClassMetadata
				.getAnnotationAttributes(AutoConfigureRestDocs.class.getName());
		BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(ManualRestDocumentation.class);
		String outputDir = (String) annotationAttributes.get("outputDir");
		if (StringUtils.hasText(outputDir)) {
			definitionBuilder.addConstructorArgValue(outputDir);
		}
		registry.registerBeanDefinition(ManualRestDocumentation.class.getName(), definitionBuilder.getBeanDefinition());
	}

}
