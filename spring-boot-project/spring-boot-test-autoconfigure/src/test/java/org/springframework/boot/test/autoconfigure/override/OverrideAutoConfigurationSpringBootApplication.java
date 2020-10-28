package org.springframework.boot.test.autoconfigure.override;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;

/**
 * Example {@link SpringBootApplication @SpringBootApplication} for use with
 * {@link OverrideAutoConfiguration @OverrideAutoConfiguration} tests.
 *

 */
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = CassandraAutoConfiguration.class)
public class OverrideAutoConfigurationSpringBootApplication {

}
