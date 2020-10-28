package org.springframework.boot.configurationsample.specific;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * An annotated getter with {@code NotEmpty} that triggers a different class type in the
 * compiler. See #11512
 *

 */
@ConfigurationProperties("specific")
public class AnnotatedGetter {

	private String name;

	@NotEmpty
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
