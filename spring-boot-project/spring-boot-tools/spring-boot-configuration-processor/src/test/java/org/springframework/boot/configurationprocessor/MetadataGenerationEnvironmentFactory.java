package org.springframework.boot.configurationprocessor;

import java.util.function.Function;

import javax.annotation.processing.ProcessingEnvironment;

import org.springframework.boot.configurationprocessor.test.TestConfigurationMetadataAnnotationProcessor;

/**
 * A factory for {@link MetadataGenerationEnvironment} against test annotations.
 *

 */
class MetadataGenerationEnvironmentFactory implements Function<ProcessingEnvironment, MetadataGenerationEnvironment> {

	@Override
	public MetadataGenerationEnvironment apply(ProcessingEnvironment environment) {
		return new MetadataGenerationEnvironment(environment,
				TestConfigurationMetadataAnnotationProcessor.CONFIGURATION_PROPERTIES_ANNOTATION,
				TestConfigurationMetadataAnnotationProcessor.NESTED_CONFIGURATION_PROPERTY_ANNOTATION,
				TestConfigurationMetadataAnnotationProcessor.DEPRECATED_CONFIGURATION_PROPERTY_ANNOTATION,
				TestConfigurationMetadataAnnotationProcessor.CONSTRUCTOR_BINDING_ANNOTATION,
				TestConfigurationMetadataAnnotationProcessor.DEFAULT_VALUE_ANNOTATION,
				TestConfigurationMetadataAnnotationProcessor.ENDPOINT_ANNOTATION,
				TestConfigurationMetadataAnnotationProcessor.READ_OPERATION_ANNOTATION,
				TestConfigurationMetadataAnnotationProcessor.NAME_ANNOTATION);
	}

}
