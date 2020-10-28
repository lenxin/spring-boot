package org.springframework.boot.ansi;

/**
 * An ANSI encodable element.
 *

 * @since 1.0.0
 */
public interface AnsiElement {

	/**
	 * @return the ANSI escape code
	 */
	@Override
	String toString();

}
