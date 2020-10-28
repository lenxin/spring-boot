package org.test;

public class SampleApplication {

	public static void main(String[] args) {
		String foo = System.getProperty("foo");
		if ("bar".equals(foo)) {
			throw new IllegalStateException("System property foo should not be available. Fork disabled");
		}
		System.out.println("I haz been run");
	}

}
