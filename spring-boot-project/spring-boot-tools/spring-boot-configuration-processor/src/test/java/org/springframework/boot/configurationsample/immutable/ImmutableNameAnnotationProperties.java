package org.springframework.boot.configurationsample.immutable;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.ConstructorBinding;
import org.springframework.boot.configurationsample.Name;

/**
 * Immutable properties making use of {@code @Name}.
 *

 */
@ConfigurationProperties("named")
@ConstructorBinding
public class ImmutableNameAnnotationProperties {

	private final String imports;

	public ImmutableNameAnnotationProperties(@Name("import") String imports) {
		this.imports = imports;
	}

	public String getImports() {
		return this.imports;
	}

}
