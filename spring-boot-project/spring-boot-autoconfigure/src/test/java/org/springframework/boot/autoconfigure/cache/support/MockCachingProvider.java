package org.springframework.boot.autoconfigure.cache.support;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * A mock {@link CachingProvider} that exposes a JSR-107 cache manager for testing
 * purposes.
 *

 */
public class MockCachingProvider implements CachingProvider {

	@Override
	public CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties) {
		return new MockCacheManager(uri, classLoader, properties);
	}

	@Override
	public ClassLoader getDefaultClassLoader() {
		return mock(ClassLoader.class);
	}

	@Override
	public URI getDefaultURI() {
		return null;
	}

	@Override
	public Properties getDefaultProperties() {
		return new Properties();
	}

	@Override
	public CacheManager getCacheManager(URI uri, ClassLoader classLoader) {
		return getCacheManager(uri, classLoader, getDefaultProperties());
	}

	@Override
	public CacheManager getCacheManager() {
		return getCacheManager(getDefaultURI(), getDefaultClassLoader());
	}

	@Override
	public void close() {
	}

	@Override
	public void close(ClassLoader classLoader) {
	}

	@Override
	public void close(URI uri, ClassLoader classLoader) {
	}

	@Override
	public boolean isSupported(OptionalFeature optionalFeature) {
		return false;
	}

	public static class MockCacheManager implements CacheManager {

		private final Map<String, Configuration<?, ?>> configurations = new HashMap<>();

		private final Map<String, Cache<?, ?>> caches = new HashMap<>();

		private final URI uri;

		private final ClassLoader classLoader;

		private final Properties properties;

		private boolean closed;

		public MockCacheManager(URI uri, ClassLoader classLoader, Properties properties) {
			this.uri = uri;
			this.classLoader = classLoader;
			this.properties = properties;
		}

		@Override
		public CachingProvider getCachingProvider() {
			throw new UnsupportedOperationException();
		}

		@Override
		public URI getURI() {
			return this.uri;
		}

		@Override
		public ClassLoader getClassLoader() {
			return this.classLoader;
		}

		@Override
		public Properties getProperties() {
			return this.properties;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(String cacheName, C configuration)
				throws IllegalArgumentException {
			this.configurations.put(cacheName, configuration);
			Cache<K, V> cache = mock(Cache.class);
			given(cache.getName()).willReturn(cacheName);
			this.caches.put(cacheName, cache);
			return cache;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyType, Class<V> valueType) {
			return (Cache<K, V>) this.caches.get(cacheName);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <K, V> Cache<K, V> getCache(String cacheName) {
			return (Cache<K, V>) this.caches.get(cacheName);
		}

		@Override
		public Iterable<String> getCacheNames() {
			return this.caches.keySet();
		}

		@Override
		public void destroyCache(String cacheName) {
			this.caches.remove(cacheName);
		}

		@Override
		public void enableManagement(String cacheName, boolean enabled) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void enableStatistics(String cacheName, boolean enabled) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void close() {
			this.closed = true;
		}

		@Override
		public boolean isClosed() {
			return this.closed;
		}

		@Override
		public <T> T unwrap(Class<T> clazz) {
			throw new UnsupportedOperationException();
		}

		public Map<String, Configuration<?, ?>> getConfigurations() {
			return this.configurations;
		}

	}

}
