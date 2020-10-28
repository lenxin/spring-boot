package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

/**
 * Example {@link SpringBootApplication @SpringBootApplication} used with
 * {@link WebMvcTest @WebMvcTest} tests.
 *

 */
@SpringBootApplication(exclude = CassandraAutoConfiguration.class)
public class ExampleWebMvcApplication {

}
