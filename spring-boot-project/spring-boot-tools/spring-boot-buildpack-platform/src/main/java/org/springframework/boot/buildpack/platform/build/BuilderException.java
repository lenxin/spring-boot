package org.springframework.boot.buildpack.platform.build;

/**
 * Exception thrown to indicate a Builder error.
 *

 * @since 2.3.0
 */
public class BuilderException extends RuntimeException {

	private final String operation;

	private final int statusCode;

	BuilderException(String operation, int statusCode) {
		super(buildMessage(operation, statusCode));
		this.operation = operation;
		this.statusCode = statusCode;
	}

	/**
	 * Return the Builder operation that failed.
	 * @return the operation description
	 */
	public String getOperation() {
		return this.operation;
	}

	/**
	 * Return the status code returned from a Builder operation.
	 * @return the statusCode the status code
	 */
	public int getStatusCode() {
		return this.statusCode;
	}

	private static String buildMessage(String operation, int statusCode) {
		StringBuilder message = new StringBuilder("Builder");
		if (operation != null && !operation.isEmpty()) {
			message.append(" lifecycle '").append(operation).append("'");
		}
		message.append(" failed with status code ").append(statusCode);
		return message.toString();
	}

}
