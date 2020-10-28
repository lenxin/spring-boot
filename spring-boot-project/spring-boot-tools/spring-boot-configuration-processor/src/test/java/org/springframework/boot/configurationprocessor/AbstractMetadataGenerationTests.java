package org.springframework.boot.configurationprocessor;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.test.TestConfigurationMetadataAnnotationProcessor;
import org.springframework.boot.testsupport.compiler.TestCompiler;

/**
 * Base test infrastructure for metadata generation tests.
 *

 */
public abstract class AbstractMetadataGenerationTests {

	@TempDir
	File tempDir;

	private TestCompiler compiler;

	@BeforeEach
	void createCompiler() throws IOException {
		this.compiler = new TestCompiler(this.tempDir);
	}

	protected TestCompiler getCompiler() {
		return this.compiler;
	}

	protected ConfigurationMetadata compile(Class<?>... types) {
		TestConfigurationMetadataAnnotationProcessor processor = new TestConfigurationMetadataAnnotationProcessor(
				this.compiler.getOutputLocation());
		this.compiler.getTask(types).call(processor);
		return processor.getMetadata();
	}

}
