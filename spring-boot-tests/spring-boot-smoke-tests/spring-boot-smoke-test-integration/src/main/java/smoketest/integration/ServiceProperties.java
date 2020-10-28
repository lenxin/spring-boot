package smoketest.integration;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
@ManagedResource
public class ServiceProperties {

	private String greeting = "Hello";

	private File inputDir;

	private File outputDir;

	@ManagedAttribute
	public String getGreeting() {
		return this.greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public File getInputDir() {
		return this.inputDir;
	}

	public void setInputDir(File inputDir) {
		this.inputDir = inputDir;
	}

	public File getOutputDir() {
		return this.outputDir;
	}

	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}

}
