package org.springframework.boot.configurationprocessor.tests;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepositoryJsonBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the configuration metadata annotation processor.
 *

 */
class ConfigurationProcessorIntegrationTests {

	private static ConfigurationMetadataRepository repository;

	@BeforeAll
	static void readMetadata() throws IOException {
		Resource resource = new ClassPathResource("META-INF/spring-configuration-metadata.json");
		assertThat(resource.exists()).isTrue();
		// Make sure the right file is detected
		assertThat(resource.getURL().toString()).contains("spring-boot-configuration-processor-tests");
		repository = ConfigurationMetadataRepositoryJsonBuilder.create(resource.getInputStream()).build();

	}

	@Test
	void extractTypeFromAnnotatedGetter() {
		ConfigurationMetadataProperty property = repository.getAllProperties().get("annotated.name");
		assertThat(property).isNotNull();
		assertThat(property.getType()).isEqualTo("java.lang.String");
	}

}
