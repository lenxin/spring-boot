package org.test;

public class SampleApplication {

	public static void main(String[] args) {
		String foo = System.getProperty("foo");
		if (!"value-from-cmd".equals(foo)) {
			throw new IllegalStateException("foo system property mismatch (got [" + foo + "]");
		}
		System.out.println("I haz been run");
	}

}
