package org.springframework.boot.loader.tools;

/**
 * A script that can be prepended to the front of a JAR file to make it executable.
 *

 * @since 1.3.0
 */
@FunctionalInterface
public interface LaunchScript {

	/**
	 * The content of the launch script as a byte array.
	 * @return the script bytes
	 */
	byte[] toByteArray();

}
