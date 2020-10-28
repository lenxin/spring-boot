package org.springframework.boot.loader.jarmode;

/**
 * Interface registered in {@code spring.factories} to provides extended 'jarmode'
 * support.
 *

 * @since 2.3.0
 */
public interface JarMode {

	/**
	 * Returns if this accepts and can run the given mode.
	 * @param mode the mode to check
	 * @return if this instance accepts the mode
	 */
	boolean accepts(String mode);

	/**
	 * Run the jar in the given mode.
	 * @param mode the mode to use
	 * @param args any program arguments
	 */
	void run(String mode, String[] args);

}
