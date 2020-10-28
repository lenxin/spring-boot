package org.springframework.boot.context.config;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit {@link Extension @Extension} to switch a test to use legacy processing.
 *


 */
public class UseLegacyProcessing implements BeforeAllCallback, AfterAllCallback {

	private static final String PROPERTY_NAME = "spring.config.use-legacy-processing";

	private String propertyValue;

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		this.propertyValue = System.setProperty(PROPERTY_NAME, "true");
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		if (this.propertyValue != null) {
			System.setProperty(PROPERTY_NAME, this.propertyValue);
		}
		else {
			System.clearProperty(PROPERTY_NAME);
		}
	}

}
