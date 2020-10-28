package org.springframework.boot.configurationprocessor;

import org.junit.jupiter.api.Test;

import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.Metadata;
import org.springframework.boot.configurationsample.incremental.BarProperties;
import org.springframework.boot.configurationsample.incremental.FooProperties;
import org.springframework.boot.configurationsample.incremental.RenamedBarProperties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Metadata generation tests for incremental builds.
 *

 */
class IncrementalBuildMetadataGenerationTests extends AbstractMetadataGenerationTests {

	@Test
	void incrementalBuild() throws Exception {
		TestProject project = new TestProject(this.tempDir, FooProperties.class, BarProperties.class);
		assertThat(project.getOutputFile(MetadataStore.METADATA_PATH).exists()).isFalse();
		ConfigurationMetadata metadata = project.fullBuild();
		assertThat(project.getOutputFile(MetadataStore.METADATA_PATH).exists()).isTrue();
		assertThat(metadata)
				.has(Metadata.withProperty("foo.counter").fromSource(FooProperties.class).withDefaultValue(0));
		assertThat(metadata)
				.has(Metadata.withProperty("bar.counter").fromSource(BarProperties.class).withDefaultValue(0));
		metadata = project.incrementalBuild(BarProperties.class);
		assertThat(metadata)
				.has(Metadata.withProperty("foo.counter").fromSource(FooProperties.class).withDefaultValue(0));
		assertThat(metadata)
				.has(Metadata.withProperty("bar.counter").fromSource(BarProperties.class).withDefaultValue(0));
		project.addSourceCode(BarProperties.class, BarProperties.class.getResourceAsStream("BarProperties.snippet"));
		metadata = project.incrementalBuild(BarProperties.class);
		assertThat(metadata).has(Metadata.withProperty("bar.extra"));
		assertThat(metadata).has(Metadata.withProperty("foo.counter").withDefaultValue(0));
		assertThat(metadata).has(Metadata.withProperty("bar.counter").withDefaultValue(0));
		project.revert(BarProperties.class);
		metadata = project.incrementalBuild(BarProperties.class);
		assertThat(metadata).isNotEqualTo(Metadata.withProperty("bar.extra"));
		assertThat(metadata).has(Metadata.withProperty("foo.counter").withDefaultValue(0));
		assertThat(metadata).has(Metadata.withProperty("bar.counter").withDefaultValue(0));
	}

	@Test
	void incrementalBuildAnnotationRemoved() throws Exception {
		TestProject project = new TestProject(this.tempDir, FooProperties.class, BarProperties.class);
		ConfigurationMetadata metadata = project.fullBuild();
		assertThat(metadata).has(Metadata.withProperty("foo.counter").withDefaultValue(0));
		assertThat(metadata).has(Metadata.withProperty("bar.counter").withDefaultValue(0));
		project.replaceText(BarProperties.class, "@ConfigurationProperties", "//@ConfigurationProperties");
		metadata = project.incrementalBuild(BarProperties.class);
		assertThat(metadata).has(Metadata.withProperty("foo.counter").withDefaultValue(0));
		assertThat(metadata).isNotEqualTo(Metadata.withProperty("bar.counter"));
	}

	@Test
	void incrementalBuildTypeRenamed() throws Exception {
		TestProject project = new TestProject(this.tempDir, FooProperties.class, BarProperties.class);
		ConfigurationMetadata metadata = project.fullBuild();
		assertThat(metadata)
				.has(Metadata.withProperty("foo.counter").fromSource(FooProperties.class).withDefaultValue(0));
		assertThat(metadata)
				.has(Metadata.withProperty("bar.counter").fromSource(BarProperties.class).withDefaultValue(0));
		assertThat(metadata).doesNotHave(Metadata.withProperty("bar.counter").fromSource(RenamedBarProperties.class));
		project.delete(BarProperties.class);
		project.add(RenamedBarProperties.class);
		metadata = project.incrementalBuild(RenamedBarProperties.class);
		assertThat(metadata)
				.has(Metadata.withProperty("foo.counter").fromSource(FooProperties.class).withDefaultValue(0));
		assertThat(metadata)
				.doesNotHave(Metadata.withProperty("bar.counter").fromSource(BarProperties.class).withDefaultValue(0));
		assertThat(metadata)
				.has(Metadata.withProperty("bar.counter").withDefaultValue(0).fromSource(RenamedBarProperties.class));
	}

}
