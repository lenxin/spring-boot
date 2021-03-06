package org.springframework.boot.gradle.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.gradle.api.JavaVersion;
import org.gradle.util.GradleVersion;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;

import org.springframework.boot.gradle.testkit.GradleBuild;
import org.springframework.boot.gradle.testkit.GradleBuildExtension;

/**
 * {@link Extension} that runs {@link TestTemplate templated tests} against multiple
 * versions of Gradle. Test classes using the extension must have a non-private and
 * non-final {@link GradleBuild} field named {@code gradleBuild}.
 *

 */
final class GradleCompatibilityExtension implements TestTemplateInvocationContextProvider {

	private static final List<String> GRADLE_VERSIONS;

	static {
		JavaVersion javaVersion = JavaVersion.current();
		if (javaVersion.isCompatibleWith(JavaVersion.VERSION_14)
				|| javaVersion.isCompatibleWith(JavaVersion.VERSION_13)) {
			GRADLE_VERSIONS = Arrays.asList("6.3", "6.4.1", "6.5.1", "6.6.1", "current");
		}
		else {
			GRADLE_VERSIONS = Arrays.asList("5.6.4", "6.3", "6.4.1", "6.5.1", "6.6.1", "current");
		}
	}

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
		return GRADLE_VERSIONS.stream().flatMap((version) -> {
			if (version.equals("current")) {
				version = GradleVersion.current().getVersion();
			}
			List<TestTemplateInvocationContext> invocationContexts = new ArrayList<>();
			invocationContexts.add(new GradleVersionTestTemplateInvocationContext(version, false));
			boolean configurationCache = AnnotationUtils
					.findAnnotation(context.getRequiredTestClass(), GradleCompatibility.class).get()
					.configurationCache();
			if (configurationCache && GradleVersion.version(version).compareTo(GradleVersion.version("6.7")) >= 0) {
				invocationContexts.add(new GradleVersionTestTemplateInvocationContext(version, true));
			}
			return invocationContexts.stream();
		});
	}

	@Override
	public boolean supportsTestTemplate(ExtensionContext context) {
		return true;
	}

	private static final class GradleVersionTestTemplateInvocationContext implements TestTemplateInvocationContext {

		private final String gradleVersion;

		private final boolean configurationCache;

		GradleVersionTestTemplateInvocationContext(String gradleVersion, boolean configurationCache) {
			this.gradleVersion = gradleVersion;
			this.configurationCache = configurationCache;
		}

		@Override
		public String getDisplayName(int invocationIndex) {
			return "Gradle " + this.gradleVersion + ((this.configurationCache) ? " --configuration-cache" : "");
		}

		@Override
		public List<Extension> getAdditionalExtensions() {
			GradleBuild gradleBuild = new GradleBuild().gradleVersion(this.gradleVersion);
			if (this.configurationCache) {
				gradleBuild.configurationCache();
			}
			return Arrays.asList(new GradleBuildFieldSetter(gradleBuild), new GradleBuildExtension());
		}

	}

}
