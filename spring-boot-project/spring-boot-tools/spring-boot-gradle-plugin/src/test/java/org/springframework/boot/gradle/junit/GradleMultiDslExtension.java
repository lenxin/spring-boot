package org.springframework.boot.gradle.junit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import org.springframework.boot.gradle.testkit.Dsl;
import org.springframework.boot.gradle.testkit.GradleBuild;
import org.springframework.boot.gradle.testkit.GradleBuildExtension;

/**
 * {@link Extension} that runs {@link TestTemplate templated tests} against the Groovy and
 * Kotlin DSLs. Test classes using the extension most have a non-private non-final
 * {@link GradleBuild} field named {@code gradleBuild}.
 *

 */
public class GradleMultiDslExtension implements TestTemplateInvocationContextProvider {

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
		return Stream.of(Dsl.values()).map(DslTestTemplateInvocationContext::new);
	}

	@Override
	public boolean supportsTestTemplate(ExtensionContext context) {
		return true;
	}

	private static final class DslTestTemplateInvocationContext implements TestTemplateInvocationContext {

		private final Dsl dsl;

		DslTestTemplateInvocationContext(Dsl dsl) {
			this.dsl = dsl;
		}

		@Override
		public List<Extension> getAdditionalExtensions() {
			return Arrays.asList(new GradleBuildFieldSetter(new GradleBuild(this.dsl)), new GradleBuildExtension());
		}

		@Override
		public String getDisplayName(int invocationIndex) {
			return this.dsl.getName();
		}

	}

}
