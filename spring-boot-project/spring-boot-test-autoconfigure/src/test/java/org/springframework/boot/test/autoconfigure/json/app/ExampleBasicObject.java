package org.springframework.boot.test.autoconfigure.json.app;

/**
 * Example object to read/write as JSON.
 *

 */
public class ExampleBasicObject {

	private String value;

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass() == getClass()) {
			return this.value.equals(((ExampleBasicObject) obj).value);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

}
