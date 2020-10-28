package org.springframework.boot.jarmode.layertools;

/**
 * Exception thrown when an unrecognized option is encountered.
 *

 */
class UnknownOptionException extends RuntimeException {

	private final String optionName;

	UnknownOptionException(String optionName) {
		this.optionName = optionName;
	}

	@Override
	public String getMessage() {
		return "--" + this.optionName;
	}

}
