package org.springframework.boot.configurationprocessor;

import org.junit.jupiter.api.Test;

import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;
import org.springframework.boot.configurationprocessor.metadata.Metadata;
import org.springframework.boot.configurationsample.immutable.DeducedImmutableClassProperties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Metadata generation tests for immutable properties deduced because they're nested.
 *


 */
class DeducedImmutablePropertiesMetadataGenerationTests extends AbstractMetadataGenerationTests {

	@Test
	void immutableSimpleProperties() {
		ConfigurationMetadata metadata = compile(DeducedImmutableClassProperties.class);
		assertThat(metadata).has(Metadata.withGroup("test").fromSource(DeducedImmutableClassProperties.class));
		assertThat(metadata).has(Metadata.withGroup("test.nested", DeducedImmutableClassProperties.Nested.class)
				.fromSource(DeducedImmutableClassProperties.class));
		assertThat(metadata).has(Metadata.withProperty("test.nested.name", String.class)
				.fromSource(DeducedImmutableClassProperties.Nested.class));
		ItemMetadata nestedMetadata = metadata.getItems().stream()
				.filter((item) -> item.getName().equals("test.nested")).findFirst().get();
		assertThat(nestedMetadata.getDefaultValue()).isNull();
	}

}
