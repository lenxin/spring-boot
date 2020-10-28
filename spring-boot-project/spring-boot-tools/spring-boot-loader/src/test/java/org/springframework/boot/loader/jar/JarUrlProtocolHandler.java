package org.springframework.boot.loader.jar;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Map;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * JUnit 5 {@link Extension} for tests that interact with Spring Boot's {@link Handler}
 * for {@code jar:} URLs. Ensures that the handler is registered prior to test execution
 * and cleans up the handler's root file cache afterwards.
 *

 */
class JarUrlProtocolHandler implements BeforeEachCallback, AfterEachCallback {

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		JarFile.registerUrlProtocolHandler();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void afterEach(ExtensionContext context) throws Exception {
		Map<File, JarFile> rootFileCache = ((SoftReference<Map<File, JarFile>>) ReflectionTestUtils
				.getField(Handler.class, "rootFileCache")).get();
		if (rootFileCache != null) {
			for (JarFile rootJarFile : rootFileCache.values()) {
				rootJarFile.close();
			}
			rootFileCache.clear();
		}
	}

}
