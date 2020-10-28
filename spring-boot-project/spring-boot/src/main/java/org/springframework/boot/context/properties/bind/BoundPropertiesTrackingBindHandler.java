package org.springframework.boot.context.properties.bind;

import java.util.function.Consumer;

import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.util.Assert;

/**
 * {@link BindHandler} that can be used to track bound configuration properties.
 *

 * @since 2.3.0
 */
public class BoundPropertiesTrackingBindHandler extends AbstractBindHandler {

	private final Consumer<ConfigurationProperty> consumer;

	public BoundPropertiesTrackingBindHandler(Consumer<ConfigurationProperty> consumer) {
		Assert.notNull(consumer, "Consumer must not be null");
		this.consumer = consumer;
	}

	@Override
	public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
		if (context.getConfigurationProperty() != null && name.equals(context.getConfigurationProperty().getName())) {
			this.consumer.accept(context.getConfigurationProperty());
		}
		return super.onSuccess(name, target, context, result);
	}

}
