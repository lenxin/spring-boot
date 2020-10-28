package org.springframework.boot.autoconfigure.data.r2dbc;

import io.r2dbc.spi.ConnectionFactory;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactoryBean;
import org.springframework.r2dbc.core.DatabaseClient;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data R2DBC Repositories.
 *

 * @see EnableR2dbcRepositories
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ConnectionFactory.class, R2dbcRepository.class })
@ConditionalOnBean(DatabaseClient.class)
@ConditionalOnProperty(prefix = "spring.data.r2dbc.repositories", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@ConditionalOnMissingBean(R2dbcRepositoryFactoryBean.class)
@Import(R2dbcRepositoriesAutoConfigureRegistrar.class)
@AutoConfigureAfter(R2dbcDataAutoConfiguration.class)
public class R2dbcRepositoriesAutoConfiguration {

}
