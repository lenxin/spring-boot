package org.springframework.boot.autoconfigure.availability;

import org.springframework.boot.availability.ApplicationAvailabilityBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration} for
 * {@link ApplicationAvailabilityBean}.
 *

 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
public class ApplicationAvailabilityAutoConfiguration {

	@Bean
	public ApplicationAvailabilityBean applicationAvailability() {
		return new ApplicationAvailabilityBean();
	}

}
