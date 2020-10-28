package org.springframework.boot.cli.command.install;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.groovy.control.CompilationFailedException;

import org.springframework.boot.cli.compiler.GroovyCompiler;
import org.springframework.boot.cli.compiler.GroovyCompilerConfiguration;

/**
 * A {@code DependencyResolver} implemented using Groovy's {@code @Grab}.
 *


 */
class GroovyGrabDependencyResolver implements DependencyResolver {

	private final GroovyCompilerConfiguration configuration;

	GroovyGrabDependencyResolver(GroovyCompilerConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public List<File> resolve(List<String> artifactIdentifiers) throws CompilationFailedException, IOException {
		GroovyCompiler groovyCompiler = new GroovyCompiler(this.configuration);
		List<File> artifactFiles = new ArrayList<>();
		if (!artifactIdentifiers.isEmpty()) {
			List<URL> initialUrls = getClassPathUrls(groovyCompiler);
			groovyCompiler.compile(createSources(artifactIdentifiers));
			List<URL> artifactUrls = getClassPathUrls(groovyCompiler);
			artifactUrls.removeAll(initialUrls);
			for (URL artifactUrl : artifactUrls) {
				artifactFiles.add(toFile(artifactUrl));
			}
		}
		return artifactFiles;
	}

	private List<URL> getClassPathUrls(GroovyCompiler compiler) {
		return new ArrayList<>(Arrays.asList(compiler.getLoader().getURLs()));
	}

	private String createSources(List<String> artifactIdentifiers) throws IOException {
		File file = File.createTempFile("SpringCLIDependency", ".groovy");
		file.deleteOnExit();
		try (OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
			for (String artifactIdentifier : artifactIdentifiers) {
				stream.write("@Grab('" + artifactIdentifier + "')");
			}
			// Dummy class to force compiler to do grab
			stream.write("class Installer {}");
		}
		// Windows paths get tricky unless you work with URI
		return file.getAbsoluteFile().toURI().toString();
	}

	private File toFile(URL url) {
		try {
			return new File(url.toURI());
		}
		catch (URISyntaxException ex) {
			return new File(url.getPath());
		}
	}

}
