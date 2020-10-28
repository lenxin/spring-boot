package org.springframework.boot.configurationsample.generic;

/**
 * A configuration properties that uses the builder pattern with a generic.
 *
 * @param <T> the type of the return type

 */
public class GenericBuilderProperties<T extends GenericBuilderProperties<T>> {

	private int number;

	public int getNumber() {
		return this.number;
	}

	@SuppressWarnings("unchecked")
	public T setNumber(int number) {
		this.number = number;
		return (T) this;
	}

}
