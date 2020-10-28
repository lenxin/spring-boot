package org.springframework.boot.gradle.tasks.bundling;

import java.io.File;

import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.specs.Spec;

import org.springframework.boot.gradle.tasks.bundling.ResolvedDependencies.DependencyDescriptor;
import org.springframework.boot.loader.tools.Layer;
import org.springframework.boot.loader.tools.Library;

/**
 * Resolver backed by a {@link LayeredSpec} that provides the destination {@link Layer}
 * for each copied {@link FileCopyDetails}.
 *




 * @see BootZipCopyAction
 */
class LayerResolver {

	private final ResolvedDependencies resolvedDependencies;

	private final LayeredSpec layeredConfiguration;

	private final Spec<FileCopyDetails> librarySpec;

	LayerResolver(ResolvedDependencies resolvedDependencies, LayeredSpec layeredConfiguration,
			Spec<FileCopyDetails> librarySpec) {
		this.resolvedDependencies = resolvedDependencies;
		this.layeredConfiguration = layeredConfiguration;
		this.librarySpec = librarySpec;
	}

	Layer getLayer(FileCopyDetails details) {
		try {
			if (this.librarySpec.isSatisfiedBy(details)) {
				return getLayer(asLibrary(details));
			}
			return getLayer(details.getSourcePath());
		}
		catch (UnsupportedOperationException ex) {
			return null;
		}
	}

	Layer getLayer(Library library) {
		return this.layeredConfiguration.asLayers().getLayer(library);
	}

	Layer getLayer(String applicationResource) {
		return this.layeredConfiguration.asLayers().getLayer(applicationResource);
	}

	Iterable<Layer> getLayers() {
		return this.layeredConfiguration.asLayers();
	}

	private Library asLibrary(FileCopyDetails details) {
		File file = details.getFile();
		DependencyDescriptor dependency = this.resolvedDependencies.find(file);
		return (dependency != null)
				? new Library(null, file, null, dependency.getCoordinates(), false, dependency.isProjectDependency())
				: new Library(file, null);
	}

}
