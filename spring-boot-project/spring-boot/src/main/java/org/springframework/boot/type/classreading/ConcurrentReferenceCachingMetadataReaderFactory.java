package org.springframework.boot.type.classreading;

import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ConcurrentReferenceHashMap;

/**
 * Caching implementation of the {@link MetadataReaderFactory} interface backed by a
 * {@link ConcurrentReferenceHashMap}, caching {@link MetadataReader} per Spring
 * {@link Resource} handle (i.e. per ".class" file).
 *

 * @since 1.4.0
 * @see CachingMetadataReaderFactory
 */
public class ConcurrentReferenceCachingMetadataReaderFactory extends SimpleMetadataReaderFactory {

	private final Map<Resource, MetadataReader> cache = new ConcurrentReferenceHashMap<>();

	/**
	 * Create a new {@link ConcurrentReferenceCachingMetadataReaderFactory} instance for
	 * the default class loader.
	 */
	public ConcurrentReferenceCachingMetadataReaderFactory() {
	}

	/**
	 * Create a new {@link ConcurrentReferenceCachingMetadataReaderFactory} instance for
	 * the given resource loader.
	 * @param resourceLoader the Spring ResourceLoader to use (also determines the
	 * ClassLoader to use)
	 */
	public ConcurrentReferenceCachingMetadataReaderFactory(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	/**
	 * Create a new {@link ConcurrentReferenceCachingMetadataReaderFactory} instance for
	 * the given class loader.
	 * @param classLoader the ClassLoader to use
	 */
	public ConcurrentReferenceCachingMetadataReaderFactory(ClassLoader classLoader) {
		super(classLoader);
	}

	@Override
	public MetadataReader getMetadataReader(Resource resource) throws IOException {
		MetadataReader metadataReader = this.cache.get(resource);
		if (metadataReader == null) {
			metadataReader = createMetadataReader(resource);
			this.cache.put(resource, metadataReader);
		}
		return metadataReader;
	}

	/**
	 * Create the meta-data reader.
	 * @param resource the source resource.
	 * @return the meta-data reader
	 * @throws IOException on error
	 */
	protected MetadataReader createMetadataReader(Resource resource) throws IOException {
		return super.getMetadataReader(resource);
	}

	/**
	 * Clear the entire MetadataReader cache, removing all cached class metadata.
	 */
	public void clearCache() {
		this.cache.clear();
	}

}
