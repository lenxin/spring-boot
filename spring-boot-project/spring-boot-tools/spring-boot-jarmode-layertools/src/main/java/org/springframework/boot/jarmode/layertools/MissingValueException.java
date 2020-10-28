package org.springframework.boot.jarmode.layertools;

/**
 * Exception thrown when a required value is not provided for an option.
 *

 */
class MissingValueException extends RuntimeException {

	private final String optionName;

	MissingValueException(String optionName) {
		this.optionName = optionName;
	}

	@Override
	public String getMessage() {
		return "--" + this.optionName;
	}

}
