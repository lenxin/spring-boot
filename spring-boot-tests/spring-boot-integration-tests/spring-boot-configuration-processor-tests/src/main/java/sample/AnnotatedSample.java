package sample;

import javax.validation.Valid;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Test that a valid type is generated if an annotation is present.
 *

 * @since 1.5.10
 */
@ConfigurationProperties("annotated")
public class AnnotatedSample {

	/**
	 * A valid name.
	 */
	private String name;

	@Valid
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
