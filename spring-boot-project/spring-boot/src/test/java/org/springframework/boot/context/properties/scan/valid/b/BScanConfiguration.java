package org.springframework.boot.context.properties.scan.valid.b;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**


 */
public class BScanConfiguration {

	public interface BProperties {

	}

	@ConstructorBinding
	@ConfigurationProperties(prefix = "b.first")
	public static class BFirstProperties implements BProperties {

		private final String name;

		public BFirstProperties(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

	}

	@ConfigurationProperties(prefix = "b.second")
	public static class BSecondProperties implements BProperties {

		private int number;

		public int getNumber() {
			return this.number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

	}

}
