package com.example.classpath;

import java.io.File;
import java.lang.management.ManagementFactory;

/**
 * Application used for testing {@code BootRun}'s classpath handling.
 *

 */
public class BootRunClasspathApplication {

	protected BootRunClasspathApplication() {

	}

	public static void main(String[] args) {
		int i = 1;
		for (String entry : ManagementFactory.getRuntimeMXBean().getClassPath().split(File.pathSeparator)) {
			System.out.println(i++ + ". " + entry);
		}
	}

}
