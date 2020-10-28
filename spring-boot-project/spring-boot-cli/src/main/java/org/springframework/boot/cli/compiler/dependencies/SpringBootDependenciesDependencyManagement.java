package org.springframework.boot.cli.compiler.dependencies;

import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelProcessor;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.locator.DefaultModelLocator;

/**
 * {@link DependencyManagement} derived from the effective pom of
 * {@code spring-boot-dependencies}.
 *

 * @since 1.3.0
 */
public class SpringBootDependenciesDependencyManagement extends MavenModelDependencyManagement {

	public SpringBootDependenciesDependencyManagement() {
		super(readModel());
	}

	private static Model readModel() {
		DefaultModelProcessor modelProcessor = new DefaultModelProcessor();
		modelProcessor.setModelLocator(new DefaultModelLocator());
		modelProcessor.setModelReader(new DefaultModelReader());

		try {
			return modelProcessor.read(SpringBootDependenciesDependencyManagement.class
					.getResourceAsStream("spring-boot-dependencies-effective-bom.xml"), null);
		}
		catch (IOException ex) {
			throw new IllegalStateException("Failed to build model from effective pom", ex);
		}
	}

}
