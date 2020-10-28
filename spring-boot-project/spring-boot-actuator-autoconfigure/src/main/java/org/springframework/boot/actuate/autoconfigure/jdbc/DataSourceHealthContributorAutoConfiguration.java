package org.springframework.boot.actuate.autoconfigure.jdbc;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.metadata.CompositeDataSourcePoolMetadataProvider;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link DataSourceHealthIndicator}.
 *






 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ JdbcTemplate.class, AbstractRoutingDataSource.class })
@ConditionalOnBean(DataSource.class)
@ConditionalOnEnabledHealthIndicator("db")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceHealthIndicatorProperties.class)
public class DataSourceHealthContributorAutoConfiguration extends
		CompositeHealthContributorConfiguration<AbstractHealthIndicator, DataSource> implements InitializingBean {

	private final Collection<DataSourcePoolMetadataProvider> metadataProviders;

	private DataSourcePoolMetadataProvider poolMetadataProvider;

	public DataSourceHealthContributorAutoConfiguration(Map<String, DataSource> dataSources,
			ObjectProvider<DataSourcePoolMetadataProvider> metadataProviders) {
		this.metadataProviders = metadataProviders.orderedStream().collect(Collectors.toList());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.poolMetadataProvider = new CompositeDataSourcePoolMetadataProvider(this.metadataProviders);
	}

	@Bean
	@ConditionalOnMissingBean(name = { "dbHealthIndicator", "dbHealthContributor" })
	public HealthContributor dbHealthContributor(Map<String, DataSource> dataSources,
			DataSourceHealthIndicatorProperties dataSourceHealthIndicatorProperties) {
		if (dataSourceHealthIndicatorProperties.isIgnoreRoutingDataSources()) {
			Map<String, DataSource> filteredDatasources = dataSources.entrySet().stream()
					.filter((e) -> !(e.getValue() instanceof AbstractRoutingDataSource))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			return createContributor(filteredDatasources);
		}
		return createContributor(dataSources);
	}

	@Override
	protected AbstractHealthIndicator createIndicator(DataSource source) {
		if (source instanceof AbstractRoutingDataSource) {
			return new RoutingDataSourceHealthIndicator();
		}
		return new DataSourceHealthIndicator(source, getValidationQuery(source));
	}

	private String getValidationQuery(DataSource source) {
		DataSourcePoolMetadata poolMetadata = this.poolMetadataProvider.getDataSourcePoolMetadata(source);
		return (poolMetadata != null) ? poolMetadata.getValidationQuery() : null;
	}

	/**
	 * {@link HealthIndicator} used for {@link AbstractRoutingDataSource} beans where we
	 * can't actually query for the status.
	 */
	static class RoutingDataSourceHealthIndicator extends AbstractHealthIndicator {

		@Override
		protected void doHealthCheck(Builder builder) throws Exception {
			builder.unknown().withDetail("routing", true);
		}

	}

}
