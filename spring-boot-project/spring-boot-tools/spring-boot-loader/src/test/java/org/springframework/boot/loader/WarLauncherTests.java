package org.springframework.boot.loader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.loader.archive.Archive;
import org.springframework.boot.loader.archive.ExplodedArchive;
import org.springframework.boot.loader.archive.JarFileArchive;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WarLauncher}.
 *

 */
class WarLauncherTests extends AbstractExecutableArchiveLauncherTests {

	@Test
	void explodedWarHasOnlyWebInfClassesAndContentsOfWebInfLibOnClasspath() throws Exception {
		File explodedRoot = explode(createJarArchive("archive.war", "WEB-INF"));
		WarLauncher launcher = new WarLauncher(new ExplodedArchive(explodedRoot, true));
		List<Archive> archives = new ArrayList<>();
		launcher.getClassPathArchivesIterator().forEachRemaining(archives::add);
		assertThat(getUrls(archives)).containsExactlyInAnyOrder(getExpectedFileUrls(explodedRoot));
		for (Archive archive : archives) {
			archive.close();
		}
	}

	@Test
	void archivedWarHasOnlyWebInfClassesAndContentsOfWebInfLibOnClasspath() throws Exception {
		File jarRoot = createJarArchive("archive.war", "WEB-INF");
		try (JarFileArchive archive = new JarFileArchive(jarRoot)) {
			WarLauncher launcher = new WarLauncher(archive);
			List<Archive> classPathArchives = new ArrayList<>();
			launcher.getClassPathArchivesIterator().forEachRemaining(classPathArchives::add);
			assertThat(getUrls(classPathArchives)).containsOnly(
					new URL("jar:" + jarRoot.toURI().toURL() + "!/WEB-INF/classes!/"),
					new URL("jar:" + jarRoot.toURI().toURL() + "!/WEB-INF/lib/foo.jar!/"),
					new URL("jar:" + jarRoot.toURI().toURL() + "!/WEB-INF/lib/bar.jar!/"),
					new URL("jar:" + jarRoot.toURI().toURL() + "!/WEB-INF/lib/baz.jar!/"));
			for (Archive classPathArchive : classPathArchives) {
				classPathArchive.close();
			}
		}
	}

	protected final URL[] getExpectedFileUrls(File explodedRoot) {
		return getExpectedFiles(explodedRoot).stream().map(this::toUrl).toArray(URL[]::new);
	}

	protected final List<File> getExpectedFiles(File parent) {
		List<File> expected = new ArrayList<>();
		expected.add(new File(parent, "WEB-INF/classes"));
		expected.add(new File(parent, "WEB-INF/lib/foo.jar"));
		expected.add(new File(parent, "WEB-INF/lib/bar.jar"));
		expected.add(new File(parent, "WEB-INF/lib/baz.jar"));
		return expected;
	}

}
