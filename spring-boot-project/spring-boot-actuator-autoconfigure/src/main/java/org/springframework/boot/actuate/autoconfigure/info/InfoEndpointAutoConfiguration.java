package org.springframework.boot.actuate.autoconfigure.info;

import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the {@link InfoEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = InfoEndpoint.class)
@AutoConfigureAfter(InfoContributorAutoConfiguration.class)
public class InfoEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public InfoEndpoint infoEndpoint(ObjectProvider<InfoContributor> infoContributors) {
		return new InfoEndpoint(infoContributors.orderedStream().collect(Collectors.toList()));
	}

}
