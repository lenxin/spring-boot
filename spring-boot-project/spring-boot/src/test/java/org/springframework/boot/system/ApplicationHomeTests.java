package org.springframework.boot.system;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.bytebuddy.ByteBuddy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.util.FileCopyUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ApplicationHome}.
 *

 */
class ApplicationHomeTests {

	@TempDir
	File tempDir;

	@Test
	void whenSourceClassIsProvidedThenApplicationHomeReflectsItsLocation() throws Exception {
		File app = new File(this.tempDir, "app");
		ApplicationHome applicationHome = createApplicationHome(app);
		assertThat(applicationHome.getDir()).isEqualTo(app);
	}

	@Test
	void whenSourceClassIsProvidedWithSpaceInItsPathThenApplicationHomeReflectsItsLocation() throws Exception {
		File app = new File(this.tempDir, "app location");
		ApplicationHome applicationHome = createApplicationHome(app);
		assertThat(applicationHome.getDir()).isEqualTo(app);
	}

	private ApplicationHome createApplicationHome(File location) throws Exception {
		File examplePackage = new File(location, "com/example");
		examplePackage.mkdirs();
		FileCopyUtils.copy(
				new ByteArrayInputStream(
						new ByteBuddy().subclass(Object.class).name("com.example.Source").make().getBytes()),
				new FileOutputStream(new File(examplePackage, "Source.class")));
		try (URLClassLoader classLoader = new URLClassLoader(new URL[] { location.toURI().toURL() })) {
			Class<?> sourceClass = classLoader.loadClass("com.example.Source");
			// Separate thread to bypass stack-based unit test detection in
			// ApplicationHome
			ExecutorService executor = Executors.newSingleThreadExecutor();
			try {
				return executor.submit(() -> new ApplicationHome(sourceClass)).get();
			}
			finally {
				executor.shutdown();
			}
		}
	}

}
