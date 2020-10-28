package org.springframework.boot.test.autoconfigure.restdocs;

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Test application used with {@link AutoConfigureRestDocs @AutoConfigureRestDocs} tests.
 *

 */
@SpringBootApplication(exclude = { CassandraAutoConfiguration.class, SecurityAutoConfiguration.class,
		ManagementWebSecurityAutoConfiguration.class })
public class RestDocsTestApplication {

}
