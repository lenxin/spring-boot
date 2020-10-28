package smoketest.bootstrapregistry.external.svn;

import java.io.IOException;
import java.util.Collections;

import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapContextClosedEvent;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistry.InstanceSupplier;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * {@link ConfigDataLoader} for subversion.
 *

 */
class SubversionConfigDataLoader implements ConfigDataLoader<SubversionConfigDataResource> {

	private static final ApplicationListener<BootstrapContextClosedEvent> closeListener = SubversionConfigDataLoader::onBootstrapContextClosed;

	SubversionConfigDataLoader(BootstrapRegistry bootstrapRegistry) {
		bootstrapRegistry.registerIfAbsent(SubversionClient.class, this::createSubversionClient);
		bootstrapRegistry.addCloseListener(closeListener);
	}

	private SubversionClient createSubversionClient(BootstrapContext bootstrapContext) {
		return new SubversionClient(bootstrapContext.get(SubversionServerCertificate.class));
	}

	@Override
	public ConfigData load(ConfigDataLoaderContext context, SubversionConfigDataResource resource)
			throws IOException, ConfigDataLocationNotFoundException {
		context.getBootstrapContext().registerIfAbsent(SubversionServerCertificate.class,
				InstanceSupplier.of(resource.getServerCertificate()));
		SubversionClient client = context.getBootstrapContext().get(SubversionClient.class);
		String loaded = client.load(resource.getLocation());
		PropertySource<?> propertySource = new MapPropertySource("svn", Collections.singletonMap("svn", loaded));
		return new ConfigData(Collections.singleton(propertySource));
	}

	private static void onBootstrapContextClosed(BootstrapContextClosedEvent event) {
		event.getApplicationContext().getBeanFactory().registerSingleton("subversionClient",
				event.getBootstrapContext().get(SubversionClient.class));
	}

}
