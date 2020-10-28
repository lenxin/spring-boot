package org.springframework.boot.autoconfigure.data.cassandra;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.city.City;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CassandraReactiveDataAutoConfiguration}.
 *



 */
class CassandraReactiveDataAutoConfigurationTests {

	private AnnotationConfigApplicationContext context;

	@AfterEach
	void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	void templateExists() {
		load("spring.data.cassandra.keyspaceName:boot_test");
		assertThat(this.context.getBeanNamesForType(ReactiveCassandraTemplate.class)).hasSize(1);
	}

	@Test
	@SuppressWarnings("unchecked")
	void entityScanShouldSetInitialEntitySet() {
		load(EntityScanConfig.class, "spring.data.cassandra.keyspaceName:boot_test");
		CassandraMappingContext mappingContext = this.context.getBean(CassandraMappingContext.class);
		Set<Class<?>> initialEntitySet = (Set<Class<?>>) ReflectionTestUtils.getField(mappingContext,
				"initialEntitySet");
		assertThat(initialEntitySet).containsOnly(City.class);
	}

	@Test
	void userTypeResolverShouldBeSet() {
		load("spring.data.cassandra.keyspaceName:boot_test");
		CassandraConverter cassandraConverter = this.context.getBean(CassandraConverter.class);
		assertThat(cassandraConverter).extracting("userTypeResolver").isInstanceOf(SimpleUserTypeResolver.class);
	}

	private void load(String... environment) {
		load(null, environment);
	}

	private void load(Class<?> config, String... environment) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		TestPropertyValues.of(environment).applyTo(ctx);
		if (config != null) {
			ctx.register(config);
		}
		ctx.register(CassandraMockConfiguration.class, CassandraAutoConfiguration.class,
				CassandraDataAutoConfiguration.class, CassandraReactiveDataAutoConfiguration.class);
		ctx.refresh();
		this.context = ctx;
	}

	@Configuration(proxyBeanMethods = false)
	@EntityScan("org.springframework.boot.autoconfigure.data.cassandra.city")
	static class EntityScanConfig {

	}

}
