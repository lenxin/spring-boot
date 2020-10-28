package org.springframework.boot.actuate.autoconfigure.system;

import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link DiskSpaceHealthIndicator}.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnEnabledHealthIndicator("diskspace")
@AutoConfigureBefore(HealthContributorAutoConfiguration.class)
@EnableConfigurationProperties(DiskSpaceHealthIndicatorProperties.class)
public class DiskSpaceHealthContributorAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(name = "diskSpaceHealthIndicator")
	public DiskSpaceHealthIndicator diskSpaceHealthIndicator(DiskSpaceHealthIndicatorProperties properties) {
		return new DiskSpaceHealthIndicator(properties.getPath(), properties.getThreshold());
	}

}
