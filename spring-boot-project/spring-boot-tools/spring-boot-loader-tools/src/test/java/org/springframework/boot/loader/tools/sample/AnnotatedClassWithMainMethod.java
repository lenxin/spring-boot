package org.springframework.boot.loader.tools.sample;

/**
 * Sample annotated class with a main method.
 *

 */
@SomeApplication
public class AnnotatedClassWithMainMethod {

	public void run() {
		System.out.println("Hello World");
	}

	public static void main(String[] args) {
		new AnnotatedClassWithMainMethod().run();
	}

}
