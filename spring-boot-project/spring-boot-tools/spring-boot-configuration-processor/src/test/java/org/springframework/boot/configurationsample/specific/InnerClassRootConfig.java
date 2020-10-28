package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Sample with a simple inner class config.
 *

 */
public class InnerClassRootConfig {

	@ConfigurationProperties(prefix = "config")
	public static class Config {

		private String name;

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
