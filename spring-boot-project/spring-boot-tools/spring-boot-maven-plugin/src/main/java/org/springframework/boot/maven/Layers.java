package org.springframework.boot.maven;

import java.io.File;

/**
 * Layer configuration options.
 *

 * @since 2.3.0
 */
public class Layers {

	private boolean enabled = true;

	private boolean includeLayerTools = true;

	private File configuration;

	/**
	 * Whether a {@code layers.idx} file should be added to the jar.
	 * @return true if a {@code layers.idx} file should be added.
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Whether to include the layer tools jar.
	 * @return true if layer tools should be included
	 */
	public boolean isIncludeLayerTools() {
		return this.includeLayerTools;
	}

	/**
	 * The location of the layers configuration file. If no file is provided, a default
	 * configuration is used with four layers: {@code application}, {@code resources},
	 * {@code snapshot-dependencies} and {@code dependencies}.
	 * @return the layers configuration file
	 */
	public File getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(File configuration) {
		this.configuration = configuration;
	}

}
