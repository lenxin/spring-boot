package org.springframework.boot.context.properties.bind.validation;

import org.springframework.util.Assert;

/**
 * Error thrown when validation fails during a bind operation.
 *


 * @since 2.0.0
 * @see ValidationErrors
 * @see ValidationBindHandler
 */
public class BindValidationException extends RuntimeException {

	private final ValidationErrors validationErrors;

	BindValidationException(ValidationErrors validationErrors) {
		super(getMessage(validationErrors));
		Assert.notNull(validationErrors, "ValidationErrors must not be null");
		this.validationErrors = validationErrors;
	}

	/**
	 * Return the validation errors that caused the exception.
	 * @return the validationErrors the validation errors
	 */
	public ValidationErrors getValidationErrors() {
		return this.validationErrors;
	}

	private static String getMessage(ValidationErrors errors) {
		StringBuilder message = new StringBuilder("Binding validation errors");
		if (errors != null) {
			message.append(" on ").append(errors.getName());
			errors.getAllErrors().forEach((error) -> message.append(String.format("%n   - %s", error)));
		}
		return message.toString();
	}

}
