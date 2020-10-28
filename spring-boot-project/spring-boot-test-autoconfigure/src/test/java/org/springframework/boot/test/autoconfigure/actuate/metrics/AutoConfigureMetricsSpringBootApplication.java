package org.springframework.boot.test.autoconfigure.actuate.metrics;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;

/**
 * Example {@link SpringBootApplication @SpringBootApplication} for use with
 * {@link AutoConfigureMetrics @AutoConfigureMetrics} tests.
 *

 */
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = CassandraAutoConfiguration.class)
class AutoConfigureMetricsSpringBootApplication {

}
