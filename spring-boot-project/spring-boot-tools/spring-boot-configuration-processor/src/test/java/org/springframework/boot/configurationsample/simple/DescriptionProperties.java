package org.springframework.boot.configurationsample.simple;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Configuration properties with various description styles.
 *

 */
@ConfigurationProperties("description")
public class DescriptionProperties {

	/**
	 * A simple description.
	 */
	private String simple;

	/**
	 * This is a lengthy description that spans across multiple lines to showcase that the
	 * line separators are cleaned automatically.
	 */
	private String multiLine;

	public String getSimple() {
		return this.simple;
	}

	public void setSimple(String simple) {
		this.simple = simple;
	}

	public String getMultiLine() {
		return this.multiLine;
	}

	public void setMultiLine(String multiLine) {
		this.multiLine = multiLine;
	}

}
