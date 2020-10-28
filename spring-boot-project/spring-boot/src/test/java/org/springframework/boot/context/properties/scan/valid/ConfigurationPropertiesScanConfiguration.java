package org.springframework.boot.context.properties.scan.valid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.scan.valid.b.BScanConfiguration;

/**
 * Used for testing {@link ConfigurationProperties @ConfigurationProperties} scanning.
 *

 */
@ConfigurationPropertiesScan
public class ConfigurationPropertiesScanConfiguration {

	@ConfigurationPropertiesScan
	@EnableConfigurationProperties({ ConfigurationPropertiesScanConfiguration.FooProperties.class })
	public static class TestConfiguration {

	}

	@ConfigurationPropertiesScan(basePackages = "org.springframework.boot.context.properties.scan.valid.a",
			basePackageClasses = BScanConfiguration.class)
	public static class DifferentPackageConfiguration {

	}

	@ConfigurationProperties(prefix = "foo")
	static class FooProperties {

	}

	@ConstructorBinding
	@ConfigurationProperties(prefix = "bar")
	static class BarProperties {

		BarProperties(String foo) {
		}

	}

	@ConfigurationProperties(prefix = "bing")
	static class BingProperties {

		BingProperties() {
		}

		BingProperties(String foo) {
		}

	}

}
