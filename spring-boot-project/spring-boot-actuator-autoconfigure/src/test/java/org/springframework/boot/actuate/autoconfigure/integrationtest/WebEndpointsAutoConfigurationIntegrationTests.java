package org.springframework.boot.actuate.autoconfigure.integrationtest;

import org.junit.jupiter.api.Test;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.health.HealthEndpointWebExtension;
import org.springframework.boot.actuate.health.ReactiveHealthEndpointWebExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the auto-configuration of web endpoints.
 *

 */
class WebEndpointsAutoConfigurationIntegrationTests {

	@Test
	void healthEndpointWebExtensionIsAutoConfigured() {
		servletWebRunner().run((context) -> context.getBean(WebEndpointTestApplication.class));
		servletWebRunner().run((context) -> assertThat(context).hasSingleBean(HealthEndpointWebExtension.class));
	}

	@Test
	void healthEndpointReactiveWebExtensionIsAutoConfigured() {
		reactiveWebRunner()
				.run((context) -> assertThat(context).hasSingleBean(ReactiveHealthEndpointWebExtension.class));
	}

	private WebApplicationContextRunner servletWebRunner() {
		return new WebApplicationContextRunner()
				.withConfiguration(UserConfigurations.of(WebEndpointTestApplication.class));
	}

	private ReactiveWebApplicationContextRunner reactiveWebRunner() {
		return new ReactiveWebApplicationContextRunner()
				.withConfiguration(UserConfigurations.of(WebEndpointTestApplication.class));
	}

	@EnableAutoConfiguration(exclude = { FlywayAutoConfiguration.class, LiquibaseAutoConfiguration.class,
			CassandraAutoConfiguration.class, CassandraDataAutoConfiguration.class, Neo4jDataAutoConfiguration.class,
			Neo4jRepositoriesAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
			MongoReactiveAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class,
			RepositoryRestMvcAutoConfiguration.class, HazelcastAutoConfiguration.class,
			ElasticsearchDataAutoConfiguration.class, SolrRepositoriesAutoConfiguration.class,
			SolrAutoConfiguration.class, RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class,
			MetricsAutoConfiguration.class })
	@SpringBootConfiguration
	static class WebEndpointTestApplication {

	}

}
