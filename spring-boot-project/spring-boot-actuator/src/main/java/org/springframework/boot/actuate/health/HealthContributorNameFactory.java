package org.springframework.boot.actuate.health;

import java.util.Locale;
import java.util.function.Function;

/**
 * Generate a sensible health indicator name based on its bean name.
 *


 * @since 2.0.0
 */
public class HealthContributorNameFactory implements Function<String, String> {

	private static final String[] SUFFIXES = { "healthindicator", "healthcontributor" };

	/**
	 * A shared singleton {@link HealthContributorNameFactory} instance.
	 */
	public static final HealthContributorNameFactory INSTANCE = new HealthContributorNameFactory();

	@Override
	public String apply(String name) {
		for (String suffix : SUFFIXES) {
			if (name != null && name.toLowerCase(Locale.ENGLISH).endsWith(suffix)) {
				return name.substring(0, name.length() - suffix.length());
			}
		}
		return name;
	}

}
