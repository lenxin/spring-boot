package org.springframework.boot.testsupport.junit.platform;

import org.springframework.util.ClassUtils;

/**
 * Base class for all reflective wrappers.
 *

 */
class ReflectiveWrapper {

	final ClassLoader classLoader;

	final Class<?> type;

	ReflectiveWrapper(ClassLoader classLoader, String type) throws Throwable {
		this.classLoader = classLoader;
		this.type = loadClass(type);
	}

	protected ReflectiveWrapper(ClassLoader classLoader, Class<?> type) throws Throwable {
		this.classLoader = classLoader;
		this.type = type;
	}

	protected final ClassLoader getClassLoader() {
		return this.classLoader;
	}

	protected final Class<?> loadClass(String type) throws ClassNotFoundException, LinkageError {
		return ClassUtils.forName(type, this.classLoader);
	}

}
