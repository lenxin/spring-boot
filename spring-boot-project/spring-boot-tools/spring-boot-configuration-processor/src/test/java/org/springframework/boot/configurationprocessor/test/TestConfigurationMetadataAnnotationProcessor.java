package org.springframework.boot.configurationprocessor.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

import org.springframework.boot.configurationprocessor.ConfigurationMetadataAnnotationProcessor;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.JsonMarshaller;

/**
 * Test {@link ConfigurationMetadataAnnotationProcessor}.
 *




 */
@SupportedAnnotationTypes({ "*" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class TestConfigurationMetadataAnnotationProcessor extends ConfigurationMetadataAnnotationProcessor {

	public static final String CONFIGURATION_PROPERTIES_ANNOTATION = "org.springframework.boot.configurationsample.ConfigurationProperties";

	public static final String NESTED_CONFIGURATION_PROPERTY_ANNOTATION = "org.springframework.boot.configurationsample.NestedConfigurationProperty";

	public static final String DEPRECATED_CONFIGURATION_PROPERTY_ANNOTATION = "org.springframework.boot.configurationsample.DeprecatedConfigurationProperty";

	public static final String CONSTRUCTOR_BINDING_ANNOTATION = "org.springframework.boot.configurationsample.ConstructorBinding";

	public static final String DEFAULT_VALUE_ANNOTATION = "org.springframework.boot.configurationsample.DefaultValue";

	public static final String ENDPOINT_ANNOTATION = "org.springframework.boot.configurationsample.Endpoint";

	public static final String READ_OPERATION_ANNOTATION = "org.springframework.boot.configurationsample.ReadOperation";

	public static final String NAME_ANNOTATION = "org.springframework.boot.configurationsample.Name";

	private ConfigurationMetadata metadata;

	private final File outputLocation;

	public TestConfigurationMetadataAnnotationProcessor(File outputLocation) {
		this.outputLocation = outputLocation;
	}

	@Override
	protected String configurationPropertiesAnnotation() {
		return CONFIGURATION_PROPERTIES_ANNOTATION;
	}

	@Override
	protected String nestedConfigurationPropertyAnnotation() {
		return NESTED_CONFIGURATION_PROPERTY_ANNOTATION;
	}

	@Override
	protected String deprecatedConfigurationPropertyAnnotation() {
		return DEPRECATED_CONFIGURATION_PROPERTY_ANNOTATION;
	}

	@Override
	protected String constructorBindingAnnotation() {
		return CONSTRUCTOR_BINDING_ANNOTATION;
	}

	@Override
	protected String defaultValueAnnotation() {
		return DEFAULT_VALUE_ANNOTATION;
	}

	@Override
	protected String endpointAnnotation() {
		return ENDPOINT_ANNOTATION;
	}

	@Override
	protected String readOperationAnnotation() {
		return READ_OPERATION_ANNOTATION;
	}

	@Override
	protected String nameAnnotation() {
		return NAME_ANNOTATION;
	}

	@Override
	protected ConfigurationMetadata writeMetaData() throws Exception {
		super.writeMetaData();
		try {
			File metadataFile = new File(this.outputLocation, "META-INF/spring-configuration-metadata.json");
			if (metadataFile.isFile()) {
				try (InputStream input = new FileInputStream(metadataFile)) {
					this.metadata = new JsonMarshaller().read(input);
				}
			}
			else {
				this.metadata = new ConfigurationMetadata();
			}
			return this.metadata;
		}
		catch (IOException ex) {
			throw new RuntimeException("Failed to read metadata from disk", ex);
		}
	}

	public ConfigurationMetadata getMetadata() {
		return this.metadata;
	}

}
