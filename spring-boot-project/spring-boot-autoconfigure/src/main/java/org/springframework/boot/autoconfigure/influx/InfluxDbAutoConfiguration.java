package org.springframework.boot.autoconfigure.influx;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.impl.InfluxDBImpl;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for InfluxDB.
 *



 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(InfluxDB.class)
@EnableConfigurationProperties(InfluxDbProperties.class)
public class InfluxDbAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty("spring.influx.url")
	public InfluxDB influxDb(InfluxDbProperties properties,
			ObjectProvider<InfluxDbOkHttpClientBuilderProvider> builder) {
		return new InfluxDBImpl(properties.getUrl(), properties.getUser(), properties.getPassword(),
				determineBuilder(builder.getIfAvailable()));
	}

	private static OkHttpClient.Builder determineBuilder(InfluxDbOkHttpClientBuilderProvider builder) {
		if (builder != null) {
			return builder.get();
		}
		return new OkHttpClient.Builder();
	}

}
