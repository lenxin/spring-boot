package org.springframework.boot.autoconfigureprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.annotation.processing.SupportedAnnotationTypes;

/**
 * Version of {@link AutoConfigureAnnotationProcessor} used for testing.
 *

 */
@SupportedAnnotationTypes({ "org.springframework.boot.autoconfigureprocessor.TestConditionalOnClass",
		"org.springframework.boot.autoconfigureprocessor.TestConditionalOnBean",
		"org.springframework.boot.autoconfigureprocessor.TestConditionalOnSingleCandidate",
		"org.springframework.boot.autoconfigureprocessor.TestConditionalOnWebApplication",
		"org.springframework.boot.autoconfigureprocessor.TestAutoConfigureBefore",
		"org.springframework.boot.autoconfigureprocessor.TestAutoConfigureAfter",
		"org.springframework.boot.autoconfigureprocessor.TestAutoConfigureOrder" })
public class TestAutoConfigureAnnotationProcessor extends AutoConfigureAnnotationProcessor {

	private final File outputLocation;

	public TestAutoConfigureAnnotationProcessor(File outputLocation) {
		this.outputLocation = outputLocation;
	}

	@Override
	protected void addAnnotations(Map<String, String> annotations) {
		put(annotations, "ConditionalOnClass", TestConditionalOnClass.class);
		put(annotations, "ConditionalOnBean", TestConditionalOnBean.class);
		put(annotations, "ConditionalOnSingleCandidate", TestConditionalOnSingleCandidate.class);
		put(annotations, "ConditionalOnWebApplication", TestConditionalOnWebApplication.class);
		put(annotations, "AutoConfigureBefore", TestAutoConfigureBefore.class);
		put(annotations, "AutoConfigureAfter", TestAutoConfigureAfter.class);
		put(annotations, "AutoConfigureOrder", TestAutoConfigureOrder.class);
	}

	private void put(Map<String, String> annotations, String key, Class<?> value) {
		annotations.put(key, value.getName());
	}

	public Properties getWrittenProperties() throws IOException {
		File file = getWrittenFile();
		if (!file.exists()) {
			return null;
		}
		try (FileInputStream inputStream = new FileInputStream(file)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		}
	}

	public File getWrittenFile() {
		return new File(this.outputLocation, PROPERTIES_PATH);
	}

}
