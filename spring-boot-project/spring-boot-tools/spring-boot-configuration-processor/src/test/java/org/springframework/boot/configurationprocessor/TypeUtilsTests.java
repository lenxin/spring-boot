package org.springframework.boot.configurationprocessor;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.function.BiConsumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.boot.configurationprocessor.TypeUtils.TypeDescriptor;
import org.springframework.boot.configurationprocessor.test.RoundEnvironmentTester;
import org.springframework.boot.configurationprocessor.test.TestableAnnotationProcessor;
import org.springframework.boot.configurationsample.generic.AbstractGenericProperties;
import org.springframework.boot.configurationsample.generic.AbstractIntermediateGenericProperties;
import org.springframework.boot.configurationsample.generic.SimpleGenericProperties;
import org.springframework.boot.testsupport.compiler.TestCompiler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TypeUtils}.
 *

 */
class TypeUtilsTests {

	@TempDir
	File tempDir;

	@Test
	void resolveTypeDescriptorOnConcreteClass() throws IOException {
		process(SimpleGenericProperties.class, (roundEnv, typeUtils) -> {
			TypeDescriptor typeDescriptor = typeUtils
					.resolveTypeDescriptor(roundEnv.getRootElement(SimpleGenericProperties.class));
			assertThat(typeDescriptor.getGenerics().keySet().stream().map(Object::toString)).containsOnly("A", "B",
					"C");
			assertThat(typeDescriptor.resolveGeneric("A")).hasToString(String.class.getName());
			assertThat(typeDescriptor.resolveGeneric("B")).hasToString(Integer.class.getName());
			assertThat(typeDescriptor.resolveGeneric("C")).hasToString(Duration.class.getName());

		});
	}

	@Test
	void resolveTypeDescriptorOnIntermediateClass() throws IOException {
		process(AbstractIntermediateGenericProperties.class, (roundEnv, typeUtils) -> {
			TypeDescriptor typeDescriptor = typeUtils
					.resolveTypeDescriptor(roundEnv.getRootElement(AbstractIntermediateGenericProperties.class));
			assertThat(typeDescriptor.getGenerics().keySet().stream().map(Object::toString)).containsOnly("A", "B",
					"C");
			assertThat(typeDescriptor.resolveGeneric("A")).hasToString(String.class.getName());
			assertThat(typeDescriptor.resolveGeneric("B")).hasToString(Integer.class.getName());
			assertThat(typeDescriptor.resolveGeneric("C")).hasToString("C");
		});
	}

	@Test
	void resolveTypeDescriptorWithOnlyGenerics() throws IOException {
		process(AbstractGenericProperties.class, (roundEnv, typeUtils) -> {
			TypeDescriptor typeDescriptor = typeUtils
					.resolveTypeDescriptor(roundEnv.getRootElement(AbstractGenericProperties.class));
			assertThat(typeDescriptor.getGenerics().keySet().stream().map(Object::toString)).containsOnly("A", "B",
					"C");

		});
	}

	private void process(Class<?> target, BiConsumer<RoundEnvironmentTester, TypeUtils> consumer) throws IOException {
		TestableAnnotationProcessor<TypeUtils> processor = new TestableAnnotationProcessor<>(consumer, TypeUtils::new);
		TestCompiler compiler = new TestCompiler(this.tempDir);
		compiler.getTask(target).call(processor);
	}

}
