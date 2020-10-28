package org.springframework.boot.autoconfigure.data.cassandra;

import java.util.Collections;
import java.util.Set;

import com.datastax.oss.driver.api.core.CqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.city.City;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ObjectUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CassandraDataAutoConfiguration}.
 *



 */
class CassandraDataAutoConfigurationTests {

	private AnnotationConfigApplicationContext context;

	@AfterEach
	void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	void templateExists() {
		load(CassandraMockConfiguration.class);
		assertThat(this.context.getBeanNamesForType(CassandraTemplate.class)).hasSize(1);
	}

	@Test
	@SuppressWarnings("unchecked")
	void entityScanShouldSetInitialEntitySet() {
		load(EntityScanConfig.class);
		CassandraMappingContext mappingContext = this.context.getBean(CassandraMappingContext.class);
		Set<Class<?>> initialEntitySet = (Set<Class<?>>) ReflectionTestUtils.getField(mappingContext,
				"initialEntitySet");
		assertThat(initialEntitySet).containsOnly(City.class);
	}

	@Test
	void userTypeResolverShouldBeSet() {
		load();
		CassandraConverter cassandraConverter = this.context.getBean(CassandraConverter.class);
		assertThat(cassandraConverter).extracting("userTypeResolver").isInstanceOf(SimpleUserTypeResolver.class);
	}

	@Test
	void codecRegistryShouldBeSet() {
		load();
		CassandraConverter cassandraConverter = this.context.getBean(CassandraConverter.class);
		assertThat(cassandraConverter.getCodecRegistry())
				.isSameAs(this.context.getBean(CassandraMockConfiguration.class).codecRegistry);
	}

	@Test
	void defaultConversions() {
		load();
		CassandraTemplate template = this.context.getBean(CassandraTemplate.class);
		assertThat(template.getConverter().getConversionService().canConvert(Person.class, String.class)).isFalse();
	}

	@Test
	void customConversions() {
		load(CustomConversionConfig.class);
		CassandraTemplate template = this.context.getBean(CassandraTemplate.class);
		assertThat(template.getConverter().getConversionService().canConvert(Person.class, String.class)).isTrue();
	}

	@Test
	void clusterDoesNotExist() {
		this.context = new AnnotationConfigApplicationContext(CassandraDataAutoConfiguration.class);
		assertThat(this.context.getBeansOfType(CqlSession.class)).isEmpty();
	}

	void load(Class<?>... config) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		TestPropertyValues.of("spring.data.cassandra.keyspaceName:boot_test").applyTo(ctx);
		if (!ObjectUtils.isEmpty(config)) {
			ctx.register(config);
		}
		ctx.register(CassandraMockConfiguration.class, CassandraAutoConfiguration.class,
				CassandraDataAutoConfiguration.class);
		ctx.refresh();
		this.context = ctx;
	}

	@Configuration(proxyBeanMethods = false)
	@EntityScan("org.springframework.boot.autoconfigure.data.cassandra.city")
	static class EntityScanConfig {

	}

	@Configuration(proxyBeanMethods = false)
	static class CustomConversionConfig {

		@Bean
		CassandraCustomConversions myCassandraCustomConversions() {
			return new CassandraCustomConversions(Collections.singletonList(new MyConverter()));
		}

	}

	static class MyConverter implements Converter<Person, String> {

		@Override
		public String convert(Person o) {
			return null;
		}

	}

	static class Person {

	}

}
