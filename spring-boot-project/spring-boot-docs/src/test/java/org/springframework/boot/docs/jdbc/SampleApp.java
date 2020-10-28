package org.springframework.boot.docs.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * A sample {@link SpringBootConfiguration @ConfigurationProperties} that only enables the
 * auto-configuration for the {@link DataSource}.
 *

 */
@SpringBootConfiguration
@ImportAutoConfiguration(DataSourceAutoConfiguration.class)
class SampleApp {

}
