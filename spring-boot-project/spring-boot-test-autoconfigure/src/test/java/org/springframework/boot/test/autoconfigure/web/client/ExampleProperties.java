package org.springframework.boot.test.autoconfigure.web.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Example {@link ConstructorBinding constructor-bound}
 * {@link ConfigurationProperties @ConfigurationProperties} used to test the use of
 * configuration properties scan with sliced test.
 *

 */
@ConstructorBinding
@ConfigurationProperties("example")
public class ExampleProperties {

	private final String name;

	public ExampleProperties(@DefaultValue("test") String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
