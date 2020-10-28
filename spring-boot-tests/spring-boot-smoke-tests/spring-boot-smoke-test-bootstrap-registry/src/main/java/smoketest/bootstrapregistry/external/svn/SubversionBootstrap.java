package smoketest.bootstrapregistry.external.svn;

import java.util.function.Function;

import org.springframework.boot.BootstrapContext;
import org.springframework.boot.Bootstrapper;

/**
 * Allows the user to register a {@link Bootstrapper} with a custom
 * {@link SubversionClient}.
 *

 */
public final class SubversionBootstrap {

	private SubversionBootstrap() {
	}

	/**
	 * Return a {@link Bootstrapper} for the given client factory.
	 * @param clientFactory the client factory
	 * @return a {@link Bootstrapper} instance
	 */
	public static Bootstrapper withCustomClient(Function<SubversionServerCertificate, SubversionClient> clientFactory) {
		return (registry) -> registry.register(SubversionClient.class,
				(bootstrapContext) -> createSubversionClient(bootstrapContext, clientFactory));
	}

	private static SubversionClient createSubversionClient(BootstrapContext bootstrapContext,
			Function<SubversionServerCertificate, SubversionClient> clientFactory) {
		return clientFactory.apply(bootstrapContext.get(SubversionServerCertificate.class));
	}

}
