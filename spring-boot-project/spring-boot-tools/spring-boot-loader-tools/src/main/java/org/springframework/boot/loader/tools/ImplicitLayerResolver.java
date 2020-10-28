package org.springframework.boot.loader.tools;

/**
 * Implementation of {@link Layers} that uses implicit rules.
 *


 */
class ImplicitLayerResolver extends StandardLayers {

	private static final String SPRING_BOOT_LOADER_PREFIX = "org/springframework/boot/loader/";

	@Override
	public Layer getLayer(String name) {
		if (name.startsWith(SPRING_BOOT_LOADER_PREFIX)) {
			return SPRING_BOOT_LOADER;
		}
		return APPLICATION;
	}

	@Override
	public Layer getLayer(Library library) {
		if (library.isLocal()) {
			return APPLICATION;
		}
		if (library.getName().contains("SNAPSHOT.")) {
			return SNAPSHOT_DEPENDENCIES;
		}
		return DEPENDENCIES;
	}

}
