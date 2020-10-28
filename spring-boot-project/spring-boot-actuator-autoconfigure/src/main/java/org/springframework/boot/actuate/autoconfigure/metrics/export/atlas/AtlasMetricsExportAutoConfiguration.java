package org.springframework.boot.actuate.autoconfigure.metrics.export.atlas;

import com.netflix.spectator.atlas.AtlasConfig;
import io.micrometer.atlas.AtlasMeterRegistry;
import io.micrometer.core.instrument.Clock;

import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.ConditionalOnEnabledMetricsExport;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to Atlas.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(AtlasMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("atlas")
@EnableConfigurationProperties(AtlasProperties.class)
public class AtlasMetricsExportAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public AtlasConfig atlasConfig(AtlasProperties atlasProperties) {
		return new AtlasPropertiesConfigAdapter(atlasProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public AtlasMeterRegistry atlasMeterRegistry(AtlasConfig atlasConfig, Clock clock) {
		return new AtlasMeterRegistry(atlasConfig, clock);
	}

}
