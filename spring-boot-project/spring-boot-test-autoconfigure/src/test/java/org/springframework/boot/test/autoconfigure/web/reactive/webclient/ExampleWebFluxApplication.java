package org.springframework.boot.test.autoconfigure.web.reactive.webclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

/**
 * Example {@link SpringBootApplication @SpringBootApplication} used with
 * {@link WebFluxTest @WebFluxTest} tests.
 *

 */
@SpringBootApplication(exclude = CassandraAutoConfiguration.class)
public class ExampleWebFluxApplication {

}
