package com.example.jvmargs;

import java.lang.management.ManagementFactory;

/**
 * Application used for testing {@code BootRun}'s JVM argument handling.
 *

 */
public class BootRunJvmArgsApplication {

	protected BootRunJvmArgsApplication() {

	}

	public static void main(String[] args) {
		int i = 1;
		for (String entry : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
			System.out.println(i++ + ". " + entry);
		}
	}

}
