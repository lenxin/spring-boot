package org.springframework.boot.testsupport.junit.platform;

/**
 * Reflective mirror of JUnit 5's {@code SummaryGeneratingListener}.
 *

 * @since 2.2.0
 */
public class SummaryGeneratingListener extends ReflectiveWrapper {

	final Object instance;

	public SummaryGeneratingListener(ClassLoader classLoader) throws Throwable {
		super(classLoader, "org.junit.platform.launcher.listeners.SummaryGeneratingListener");
		this.instance = this.type.getDeclaredConstructor().newInstance();
	}

	public TestExecutionSummary getSummary() throws Throwable {
		return new TestExecutionSummary(getClassLoader(), this.type.getMethod("getSummary").invoke(this.instance));
	}

}
