package org.springframework.boot.context.config;

import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;

/**
 * Exception thrown if legacy processing must be used.
 *


 */
final class UseLegacyConfigProcessingException extends ConfigDataException {

	/**
	 * The property name used to trigger legacy processing.
	 */
	static final ConfigurationPropertyName PROPERTY_NAME = ConfigurationPropertyName
			.of("spring.config.use-legacy-processing");

	private static final Bindable<Boolean> BOOLEAN = Bindable.of(Boolean.class);

	private static final UseLegacyProcessingBindHandler BIND_HANDLER = new UseLegacyProcessingBindHandler();

	private final ConfigurationProperty configurationProperty;

	UseLegacyConfigProcessingException(ConfigurationProperty configurationProperty) {
		super("Legacy processing requested from " + configurationProperty, null);
		this.configurationProperty = configurationProperty;
	}

	/**
	 * Return the source configuration property that requested the use of legacy
	 * processing.
	 * @return the configurationProperty the configuration property
	 */
	ConfigurationProperty getConfigurationProperty() {
		return this.configurationProperty;
	}

	/**
	 * Throw a new {@link UseLegacyConfigProcessingException} instance if
	 * {@link #PROPERTY_NAME} binds to {@code true}.
	 * @param binder the binder to use
	 */
	static void throwIfRequested(Binder binder) {
		try {
			binder.bind(PROPERTY_NAME, BOOLEAN, BIND_HANDLER);
		}
		catch (BindException ex) {
			if (ex.getCause() instanceof UseLegacyConfigProcessingException) {
				throw (UseLegacyConfigProcessingException) ex.getCause();
			}
			throw ex;
		}
	}

	/**
	 * {@link BindHandler} used to check for legacy processing properties.
	 */
	private static class UseLegacyProcessingBindHandler implements BindHandler {

		@Override
		public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context,
				Object result) {
			if (Boolean.TRUE.equals(result)) {
				throw new UseLegacyConfigProcessingException(context.getConfigurationProperty());
			}
			return result;
		}

	}

}
