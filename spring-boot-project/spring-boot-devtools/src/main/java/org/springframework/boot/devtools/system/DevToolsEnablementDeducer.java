package org.springframework.boot.devtools.system;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Utility to deduce if DevTools should be enabled in the current context.
 *

 * @since 2.2.0
 */
public final class DevToolsEnablementDeducer {

	private static final Set<String> SKIPPED_STACK_ELEMENTS;

	static {
		Set<String> skipped = new LinkedHashSet<>();
		skipped.add("org.junit.runners.");
		skipped.add("org.junit.platform.");
		skipped.add("org.springframework.boot.test.");
		skipped.add("cucumber.runtime.");
		SKIPPED_STACK_ELEMENTS = Collections.unmodifiableSet(skipped);
	}

	private DevToolsEnablementDeducer() {
	}

	/**
	 * Checks if a specific {@link StackTraceElement} in the current thread's stacktrace
	 * should cause devtools to be disabled.
	 * @param thread the current thread
	 * @return {@code true} if devtools should be enabled
	 */
	public static boolean shouldEnable(Thread thread) {
		for (StackTraceElement element : thread.getStackTrace()) {
			if (isSkippedStackElement(element)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isSkippedStackElement(StackTraceElement element) {
		for (String skipped : SKIPPED_STACK_ELEMENTS) {
			if (element.getClassName().startsWith(skipped)) {
				return true;
			}
		}
		return false;
	}

}
