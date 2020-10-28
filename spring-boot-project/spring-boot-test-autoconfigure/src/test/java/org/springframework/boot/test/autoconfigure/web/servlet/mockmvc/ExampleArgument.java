package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

/**

 */
public class ExampleArgument {

	private final String value;

	public ExampleArgument(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

}
