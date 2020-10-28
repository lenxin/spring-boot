package org.springframework.boot.autoconfigure.web;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.web.ResourceProperties.Cache;
import org.springframework.http.CacheControl;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ResourceProperties}.
 *


 */
class ResourcePropertiesTests {

	private final ResourceProperties properties = new ResourceProperties();

	@Test
	void resourceChainNoCustomization() {
		assertThat(this.properties.getChain().getEnabled()).isNull();
	}

	@Test
	void resourceChainStrategyEnabled() {
		this.properties.getChain().getStrategy().getFixed().setEnabled(true);
		assertThat(this.properties.getChain().getEnabled()).isTrue();
	}

	@Test
	void resourceChainEnabled() {
		this.properties.getChain().setEnabled(true);
		assertThat(this.properties.getChain().getEnabled()).isTrue();
	}

	@Test
	void resourceChainDisabled() {
		this.properties.getChain().setEnabled(false);
		assertThat(this.properties.getChain().getEnabled()).isFalse();
	}

	@Test
	void defaultStaticLocationsAllEndWithTrailingSlash() {
		assertThat(this.properties.getStaticLocations()).allMatch((location) -> location.endsWith("/"));
	}

	@Test
	void customStaticLocationsAreNormalizedToEndWithTrailingSlash() {
		this.properties.setStaticLocations(new String[] { "/foo", "/bar", "/baz/" });
		String[] actual = this.properties.getStaticLocations();
		assertThat(actual).containsExactly("/foo/", "/bar/", "/baz/");
	}

	@Test
	void emptyCacheControl() {
		CacheControl cacheControl = this.properties.getCache().getCachecontrol().toHttpCacheControl();
		assertThat(cacheControl).isNull();
	}

	@Test
	void cacheControlAllPropertiesSet() {
		Cache.Cachecontrol properties = this.properties.getCache().getCachecontrol();
		properties.setMaxAge(Duration.ofSeconds(4));
		properties.setCachePrivate(true);
		properties.setCachePublic(true);
		properties.setMustRevalidate(true);
		properties.setNoTransform(true);
		properties.setProxyRevalidate(true);
		properties.setSMaxAge(Duration.ofSeconds(5));
		properties.setStaleIfError(Duration.ofSeconds(6));
		properties.setStaleWhileRevalidate(Duration.ofSeconds(7));
		CacheControl cacheControl = properties.toHttpCacheControl();
		assertThat(cacheControl.getHeaderValue())
				.isEqualTo("max-age=4, must-revalidate, no-transform, public, private, proxy-revalidate,"
						+ " s-maxage=5, stale-if-error=6, stale-while-revalidate=7");
	}

	@Test
	void invalidCacheControlCombination() {
		Cache.Cachecontrol properties = this.properties.getCache().getCachecontrol();
		properties.setMaxAge(Duration.ofSeconds(4));
		properties.setNoStore(true);
		CacheControl cacheControl = properties.toHttpCacheControl();
		assertThat(cacheControl.getHeaderValue()).isEqualTo("no-store");
	}

}
