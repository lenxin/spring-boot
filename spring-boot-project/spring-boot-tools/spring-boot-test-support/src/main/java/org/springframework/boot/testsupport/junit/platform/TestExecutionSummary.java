package org.springframework.boot.testsupport.junit.platform;

import java.util.List;

import org.springframework.util.CollectionUtils;

/**
 * Reflective mirror of JUnit 5's {@code TestExecutionSummary}.
 *

 * @since 2.2.0
 */
public class TestExecutionSummary extends ReflectiveWrapper {

	private final Class<?> failureType;

	private final Object instance;

	TestExecutionSummary(ClassLoader classLoader, Object instance) throws Throwable {
		super(classLoader, "org.junit.platform.launcher.listeners.TestExecutionSummary");
		this.failureType = loadClass("org.junit.platform.launcher.listeners.TestExecutionSummary$Failure");
		this.instance = instance;
	}

	public Throwable getFailure() throws Throwable {
		List<?> failures = (List<?>) this.type.getMethod("getFailures").invoke(this.instance);
		if (!CollectionUtils.isEmpty(failures)) {
			Object failure = failures.get(0);
			return (Throwable) this.failureType.getMethod("getException").invoke(failure);
		}
		return null;
	}

}
