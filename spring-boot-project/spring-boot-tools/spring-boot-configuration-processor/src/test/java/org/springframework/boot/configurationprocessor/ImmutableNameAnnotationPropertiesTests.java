package org.springframework.boot.configurationprocessor;

import org.junit.jupiter.api.Test;

import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.Metadata;
import org.springframework.boot.configurationsample.immutable.ImmutableNameAnnotationProperties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Metadata generation tests for immutable properties using {@code @Name}.
 *

 */
public class ImmutableNameAnnotationPropertiesTests extends AbstractMetadataGenerationTests {

	@Test
	void immutableNameAnnotationProperties() {
		ConfigurationMetadata metadata = compile(ImmutableNameAnnotationProperties.class);
		assertThat(metadata).has(Metadata.withProperty("named.import", String.class)
				.fromSource(ImmutableNameAnnotationProperties.class));
	}

}
