package org.springframework.boot.loader.jarmode;

import java.util.List;

import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ClassUtils;

/**
 * Delegate class used to launch the fat jar in a specific mode.
 *

 * @since 2.3.0
 */
public final class JarModeLauncher {

	static final String DISABLE_SYSTEM_EXIT = JarModeLauncher.class.getName() + ".DISABLE_SYSTEM_EXIT";

	private JarModeLauncher() {
	}

	public static void main(String[] args) {
		String mode = System.getProperty("jarmode");
		List<JarMode> candidates = SpringFactoriesLoader.loadFactories(JarMode.class,
				ClassUtils.getDefaultClassLoader());
		for (JarMode candidate : candidates) {
			if (candidate.accepts(mode)) {
				candidate.run(mode, args);
				return;
			}
		}
		System.err.println("Unsupported jarmode '" + mode + "'");
		if (!Boolean.getBoolean(DISABLE_SYSTEM_EXIT)) {
			System.exit(1);
		}
	}

}
