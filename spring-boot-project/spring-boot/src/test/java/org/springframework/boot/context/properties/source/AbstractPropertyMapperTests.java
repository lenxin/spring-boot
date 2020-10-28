package org.springframework.boot.context.properties.source;

import java.util.List;

/**
 * Abstract base class for {@link PropertyMapper} tests.
 *



 */
public abstract class AbstractPropertyMapperTests {

	protected abstract PropertyMapper getMapper();

	protected final List<String> mapConfigurationPropertyName(String configurationPropertyName) {
		return getMapper().map(ConfigurationPropertyName.of(configurationPropertyName));
	}

	protected final String mapPropertySourceName(String propertySourceName) {
		return getMapper().map(propertySourceName).toString();
	}

}
