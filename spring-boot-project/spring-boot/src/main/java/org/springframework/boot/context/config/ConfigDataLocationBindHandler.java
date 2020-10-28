package org.springframework.boot.context.config;

import java.util.List;

import org.springframework.boot.context.properties.bind.AbstractBindHandler;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.origin.Origin;

/**
 * {@link BindHandler} to set the {@link Origin} of bound {@link ConfigDataLocation}
 * objects.
 *

 */
class ConfigDataLocationBindHandler extends AbstractBindHandler {

	@Override
	@SuppressWarnings("unchecked")
	public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
		if (result instanceof ConfigDataLocation) {
			return withOrigin(context, (ConfigDataLocation) result);
		}
		if (result instanceof List) {
			List<Object> list = (List<Object>) result;
			for (int i = 0; i < list.size(); i++) {
				Object element = list.get(i);
				if (element instanceof ConfigDataLocation) {
					list.set(i, withOrigin(context, (ConfigDataLocation) element));
				}
			}
		}
		if (result instanceof ConfigDataLocation[]) {
			ConfigDataLocation[] locations = (ConfigDataLocation[]) result;
			for (int i = 0; i < locations.length; i++) {
				locations[i] = withOrigin(context, locations[i]);
			}
		}
		return result;
	}

	private ConfigDataLocation withOrigin(BindContext context, ConfigDataLocation result) {
		if (result.getOrigin() != null) {
			return result;
		}
		Origin origin = Origin.from(context.getConfigurationProperty());
		return result.withOrigin(origin);
	}

}
