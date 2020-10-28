package com.example.main;

/**
 * Application used for testing {@code BootRun}'s main class configuration.
 *

 */
public class CustomMainClass {

	protected CustomMainClass() {

	}

	public static void main(String[] args) {
		System.out.println(CustomMainClass.class.getName());
	}

}
