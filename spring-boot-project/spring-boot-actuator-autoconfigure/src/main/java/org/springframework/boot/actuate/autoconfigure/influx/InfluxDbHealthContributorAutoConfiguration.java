package org.springframework.boot.actuate.autoconfigure.influx;

import java.util.Map;

import org.influxdb.InfluxDB;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.influx.InfluxDbHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link InfluxDbHealthIndicator}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(InfluxDB.class)
@ConditionalOnBean(InfluxDB.class)
@ConditionalOnEnabledHealthIndicator("influxdb")
@AutoConfigureAfter(InfluxDbAutoConfiguration.class)
public class InfluxDbHealthContributorAutoConfiguration
		extends CompositeHealthContributorConfiguration<InfluxDbHealthIndicator, InfluxDB> {

	@Bean
	@ConditionalOnMissingBean(name = { "influxDbHealthIndicator", "influxDbHealthContributor" })
	public HealthContributor influxDbHealthContributor(Map<String, InfluxDB> influxDbs) {
		return createContributor(influxDbs);
	}

}
