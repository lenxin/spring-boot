package org.springframework.boot.context.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Test;

import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistry.InstanceSupplier;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.env.PropertySource;
import org.springframework.mock.env.MockPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ConfigDataLoaders}.
 *


 */
class ConfigDataLoadersTests {

	private DeferredLogFactory logFactory = Supplier::get;

	private DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();

	private ConfigDataLoaderContext context = mock(ConfigDataLoaderContext.class);

	@Test
	void createWhenLoaderHasLogParameterInjectsLog() {
		new ConfigDataLoaders(this.logFactory, this.bootstrapContext,
				Arrays.asList(LoggingConfigDataLoader.class.getName()));
	}

	@Test
	void createWhenLoaderHasBootstrapParametersInjectsBootstrapContext() {
		new ConfigDataLoaders(this.logFactory, this.bootstrapContext,
				Arrays.asList(BootstrappingConfigDataLoader.class.getName()));
		assertThat(this.bootstrapContext.get(String.class)).isEqualTo("boot");
	}

	@Test
	void loadWhenSingleLoaderSupportsLocationReturnsLoadedConfigData() throws Exception {
		TestConfigDataResource location = new TestConfigDataResource("test");
		ConfigDataLoaders loaders = new ConfigDataLoaders(this.logFactory, this.bootstrapContext,
				Arrays.asList(TestConfigDataLoader.class.getName()));
		ConfigData loaded = loaders.load(this.context, location);
		assertThat(getLoader(loaded)).isInstanceOf(TestConfigDataLoader.class);
	}

	@Test
	void loadWhenMultipleLoadersSupportLocationThrowsException() throws Exception {
		TestConfigDataResource location = new TestConfigDataResource("test");
		ConfigDataLoaders loaders = new ConfigDataLoaders(this.logFactory, this.bootstrapContext,
				Arrays.asList(LoggingConfigDataLoader.class.getName(), TestConfigDataLoader.class.getName()));
		assertThatIllegalStateException().isThrownBy(() -> loaders.load(this.context, location))
				.withMessageContaining("Multiple loaders found for resource 'test'");
	}

	@Test
	void loadWhenNoLoaderSupportsLocationThrowsException() {
		TestConfigDataResource location = new TestConfigDataResource("test");
		ConfigDataLoaders loaders = new ConfigDataLoaders(this.logFactory, this.bootstrapContext,
				Arrays.asList(NonLoadableConfigDataLoader.class.getName()));
		assertThatIllegalStateException().isThrownBy(() -> loaders.load(this.context, location))
				.withMessage("No loader found for resource 'test'");
	}

	@Test
	void loadWhenGenericTypeDoesNotMatchSkipsLoader() throws Exception {
		TestConfigDataResource location = new TestConfigDataResource("test");
		ConfigDataLoaders loaders = new ConfigDataLoaders(this.logFactory, this.bootstrapContext,
				Arrays.asList(OtherConfigDataLoader.class.getName(), SpecificConfigDataLoader.class.getName()));
		ConfigData loaded = loaders.load(this.context, location);
		assertThat(getLoader(loaded)).isInstanceOf(SpecificConfigDataLoader.class);
	}

	private ConfigDataLoader<?> getLoader(ConfigData loaded) {
		return (ConfigDataLoader<?>) loaded.getPropertySources().get(0).getProperty("loader");
	}

	private static ConfigData createConfigData(ConfigDataLoader<?> loader, ConfigDataResource resource) {
		MockPropertySource propertySource = new MockPropertySource();
		propertySource.setProperty("loader", loader);
		propertySource.setProperty("resource", resource);
		List<PropertySource<?>> propertySources = Arrays.asList(propertySource);
		return new ConfigData(propertySources);
	}

	static class TestConfigDataResource extends ConfigDataResource {

		private final String value;

		TestConfigDataResource(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}

	}

	static class OtherConfigDataResource extends ConfigDataResource {

	}

	static class LoggingConfigDataLoader implements ConfigDataLoader<ConfigDataResource> {

		LoggingConfigDataLoader(Log log) {
			assertThat(log).isNotNull();
		}

		@Override
		public ConfigData load(ConfigDataLoaderContext context, ConfigDataResource resource) throws IOException {
			throw new AssertionError("Unexpected call");
		}

	}

	static class BootstrappingConfigDataLoader implements ConfigDataLoader<ConfigDataResource> {

		BootstrappingConfigDataLoader(ConfigurableBootstrapContext configurableBootstrapContext,
				BootstrapRegistry bootstrapRegistry, BootstrapContext bootstrapContext) {
			assertThat(configurableBootstrapContext).isNotNull();
			assertThat(bootstrapRegistry).isNotNull();
			assertThat(bootstrapContext).isNotNull();
			assertThat(configurableBootstrapContext).isEqualTo(bootstrapRegistry).isEqualTo(bootstrapContext);
			bootstrapRegistry.register(String.class, InstanceSupplier.of("boot"));
		}

		@Override
		public ConfigData load(ConfigDataLoaderContext context, ConfigDataResource resource) throws IOException {
			throw new AssertionError("Unexpected call");
		}

	}

	static class TestConfigDataLoader implements ConfigDataLoader<ConfigDataResource> {

		@Override
		public ConfigData load(ConfigDataLoaderContext context, ConfigDataResource resource) throws IOException {
			return createConfigData(this, resource);
		}

	}

	static class NonLoadableConfigDataLoader extends TestConfigDataLoader {

		@Override
		public boolean isLoadable(ConfigDataLoaderContext context, ConfigDataResource resource) {
			return false;
		}

	}

	static class SpecificConfigDataLoader implements ConfigDataLoader<TestConfigDataResource> {

		@Override
		public ConfigData load(ConfigDataLoaderContext context, TestConfigDataResource location) throws IOException {
			return createConfigData(this, location);
		}

	}

	static class OtherConfigDataLoader implements ConfigDataLoader<OtherConfigDataResource> {

		@Override
		public ConfigData load(ConfigDataLoaderContext context, OtherConfigDataResource location) throws IOException {
			return createConfigData(this, location);
		}

	}

}
