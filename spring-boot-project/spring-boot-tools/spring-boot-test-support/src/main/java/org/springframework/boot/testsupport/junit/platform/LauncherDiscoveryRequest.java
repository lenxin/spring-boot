package org.springframework.boot.testsupport.junit.platform;

/**
 * Reflective mirror of JUnit 5's {@code LauncherDiscoveryRequest}.
 *

 * @since 2.2.0
 */
public class LauncherDiscoveryRequest extends ReflectiveWrapper {

	final Object instance;

	LauncherDiscoveryRequest(ClassLoader classLoader, Object instance) throws Throwable {
		super(classLoader, "org.junit.platform.launcher.LauncherDiscoveryRequest");
		this.instance = instance;
	}

}
