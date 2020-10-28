package org.springframework.boot.buildpack.platform.build;

/**
 * Image types.
 *

 */
enum ImageType {

	/**
	 * Builder image.
	 */
	BUILDER("builder image"),

	/**
	 * Run image.
	 */
	RUNNER("run image");

	private final String description;

	ImageType(String description) {
		this.description = description;
	}

	String getDescription() {
		return this.description;
	}

}
