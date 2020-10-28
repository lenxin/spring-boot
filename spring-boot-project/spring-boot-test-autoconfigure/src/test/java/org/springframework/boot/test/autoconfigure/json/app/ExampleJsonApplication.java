package org.springframework.boot.test.autoconfigure.json.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

/**
 * Example {@link SpringBootApplication @SpringBootApplication} for use with
 * {@link JsonTest @JsonTest} tests.
 *

 */
@SpringBootApplication(exclude = CassandraAutoConfiguration.class)
public class ExampleJsonApplication {

}
