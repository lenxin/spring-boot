package org.springframework.boot.actuate.context.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

/**
 * Used for testing the {@link ConfigurationPropertiesReportEndpoint} endpoint with
 * validated {@link ConfigurationProperties @ConfigurationProperties}.
 *

 */
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "validated")
public class ValidatedConstructorBindingProperties {

	private final String name;

	ValidatedConstructorBindingProperties(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
