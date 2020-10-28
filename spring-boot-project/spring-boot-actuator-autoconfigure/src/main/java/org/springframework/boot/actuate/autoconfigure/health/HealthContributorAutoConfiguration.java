package org.springframework.boot.actuate.autoconfigure.health;

import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.PingHealthIndicator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link HealthContributor health
 * contributors}.
 *

 * @since 2.2.0
 */
@Configuration(proxyBeanMethods = false)
public class HealthContributorAutoConfiguration {

	@Bean
	@ConditionalOnEnabledHealthIndicator("ping")
	public PingHealthIndicator pingHealthContributor() {
		return new PingHealthIndicator();
	}

}
