package org.springframework.boot.autoconfigure.session;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.boot.WebApplicationType;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * Mappings between {@link StoreType} and {@code @Configuration}.
 *


 */
final class SessionStoreMappings {

	private static final Map<StoreType, Configurations> MAPPINGS;

	static {
		Map<StoreType, Configurations> mappings = new EnumMap<>(StoreType.class);
		mappings.put(StoreType.REDIS,
				new Configurations(RedisSessionConfiguration.class, RedisReactiveSessionConfiguration.class));
		mappings.put(StoreType.MONGODB,
				new Configurations(MongoSessionConfiguration.class, MongoReactiveSessionConfiguration.class));
		mappings.put(StoreType.JDBC, new Configurations(JdbcSessionConfiguration.class, null));
		mappings.put(StoreType.HAZELCAST, new Configurations(HazelcastSessionConfiguration.class, null));
		mappings.put(StoreType.NONE,
				new Configurations(NoOpSessionConfiguration.class, NoOpReactiveSessionConfiguration.class));
		MAPPINGS = Collections.unmodifiableMap(mappings);
	}

	private SessionStoreMappings() {
	}

	static String getConfigurationClass(WebApplicationType webApplicationType, StoreType sessionStoreType) {
		Configurations configurations = MAPPINGS.get(sessionStoreType);
		Assert.state(configurations != null, () -> "Unknown session store type " + sessionStoreType);
		return configurations.getConfiguration(webApplicationType);
	}

	static StoreType getType(WebApplicationType webApplicationType, String configurationClass) {
		return MAPPINGS.entrySet().stream()
				.filter((entry) -> ObjectUtils.nullSafeEquals(configurationClass,
						entry.getValue().getConfiguration(webApplicationType)))
				.map(Map.Entry::getKey).findFirst()
				.orElseThrow(() -> new IllegalStateException("Unknown configuration class " + configurationClass));
	}

	private static class Configurations {

		private final Class<?> servletConfiguration;

		private final Class<?> reactiveConfiguration;

		Configurations(Class<?> servletConfiguration, Class<?> reactiveConfiguration) {
			this.servletConfiguration = servletConfiguration;
			this.reactiveConfiguration = reactiveConfiguration;
		}

		String getConfiguration(WebApplicationType webApplicationType) {
			switch (webApplicationType) {
			case SERVLET:
				return getName(this.servletConfiguration);
			case REACTIVE:
				return getName(this.reactiveConfiguration);
			}
			return null;
		}

		String getName(Class<?> configuration) {
			return (configuration != null) ? configuration.getName() : null;
		}

	}

}
