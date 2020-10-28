package org.springframework.boot.test.autoconfigure.json.app;

/**
 * Example object to read/write as JSON via {@link ExampleJsonComponent}.
 *

 */
public class ExampleCustomObject {

	private String value;

	public ExampleCustomObject(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass() == getClass()) {
			return this.value.equals(((ExampleCustomObject) obj).value);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public String toString() {
		return this.value;
	}

}
