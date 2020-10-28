package org.springframework.boot.cli.compiler.grape;

import java.lang.reflect.Field;

import groovy.grape.Grape;
import groovy.grape.GrapeEngine;

/**
 * Utility to install a specific {@link Grape} engine with Groovy.
 *

 * @since 1.0.0
 */
public abstract class GrapeEngineInstaller {

	public static void install(GrapeEngine engine) {
		synchronized (Grape.class) {
			try {
				Field field = Grape.class.getDeclaredField("instance");
				field.setAccessible(true);
				field.set(null, engine);
			}
			catch (Exception ex) {
				throw new IllegalStateException("Failed to install GrapeEngine", ex);
			}
		}
	}

}
