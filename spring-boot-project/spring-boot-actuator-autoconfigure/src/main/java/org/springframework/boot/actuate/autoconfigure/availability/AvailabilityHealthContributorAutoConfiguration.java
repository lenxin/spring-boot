package org.springframework.boot.actuate.autoconfigure.availability;

import org.springframework.boot.actuate.availability.AvailabilityStateHealthIndicator;
import org.springframework.boot.actuate.availability.LivenessStateHealthIndicator;
import org.springframework.boot.actuate.availability.ReadinessStateHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link AvailabilityStateHealthIndicator}.
 *

 * @since 2.3.2
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(ApplicationAvailabilityAutoConfiguration.class)
public class AvailabilityHealthContributorAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(name = "livenessStateHealthIndicator")
	@ConditionalOnProperty(prefix = "management.health.livenessstate", name = "enabled", havingValue = "true")
	public LivenessStateHealthIndicator livenessStateHealthIndicator(ApplicationAvailability applicationAvailability) {
		return new LivenessStateHealthIndicator(applicationAvailability);
	}

	@Bean
	@ConditionalOnMissingBean(name = "readinessStateHealthIndicator")
	@ConditionalOnProperty(prefix = "management.health.readinessstate", name = "enabled", havingValue = "true")
	public ReadinessStateHealthIndicator readinessStateHealthIndicator(
			ApplicationAvailability applicationAvailability) {
		return new ReadinessStateHealthIndicator(applicationAvailability);
	}

}
