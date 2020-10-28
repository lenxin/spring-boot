package org.springframework.boot.testsupport.junit.platform;

import java.lang.reflect.Array;

/**
 * Reflective mirror of JUnit 5's {@code Launcher}.
 *

 * @since 2.2.0
 */
public class Launcher extends ReflectiveWrapper {

	private final Class<?> testExecutionListenerType;

	private final Object instance;

	public Launcher(ClassLoader classLoader) throws Throwable {
		super(classLoader, "org.junit.platform.launcher.Launcher");
		this.testExecutionListenerType = loadClass("org.junit.platform.launcher.TestExecutionListener");
		Class<?> factoryClass = loadClass("org.junit.platform.launcher.core.LauncherFactory");
		this.instance = factoryClass.getMethod("create").invoke(null);
	}

	public void registerTestExecutionListeners(SummaryGeneratingListener listener) throws Throwable {
		Object listeners = Array.newInstance(this.testExecutionListenerType, 1);
		Array.set(listeners, 0, listener.instance);
		this.type.getMethod("registerTestExecutionListeners", listeners.getClass()).invoke(this.instance, listeners);
	}

	public void execute(LauncherDiscoveryRequest request) throws Throwable {
		Object listeners = Array.newInstance(this.testExecutionListenerType, 0);
		this.type.getMethod("execute", request.type, listeners.getClass()).invoke(this.instance, request.instance,
				listeners);
	}

}
