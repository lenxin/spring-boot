package org.springframework.boot.loader.tools.sample;

/**
 * Sample class with a main method.
 *

 */
public class ClassWithMainMethod {

	public void run() {
		System.out.println("Hello World");
	}

	public static void main(String[] args) {
		new ClassWithMainMethod().run();
	}

}
