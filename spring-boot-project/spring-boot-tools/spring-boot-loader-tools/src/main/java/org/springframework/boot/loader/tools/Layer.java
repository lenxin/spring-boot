package org.springframework.boot.loader.tools;

import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * A named layer used to separate the jar when creating a Docker image.
 *


 * @since 2.3.0
 * @see Layers
 */
public class Layer {

	private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");

	private final String name;

	/**
	 * Create a new {@link Layer} instance with the specified name.
	 * @param name the name of the layer.
	 */
	public Layer(String name) {
		Assert.hasText(name, "Name must not be empty");
		Assert.isTrue(PATTERN.matcher(name).matches(), () -> "Malformed layer name '" + name + "'");
		Assert.isTrue(!name.equalsIgnoreCase("ext") && !name.toLowerCase().startsWith("springboot"),
				() -> "Layer name '" + name + "' is reserved");
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return this.name.equals(((Layer) obj).name);
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public String toString() {
		return this.name;
	}

}
