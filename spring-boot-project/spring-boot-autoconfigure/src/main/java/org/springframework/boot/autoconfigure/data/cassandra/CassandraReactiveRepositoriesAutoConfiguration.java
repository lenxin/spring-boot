package org.springframework.boot.autoconfigure.data.cassandra;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.cassandra.ReactiveSession;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import org.springframework.data.cassandra.repository.support.ReactiveCassandraRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Cassandra Reactive
 * Repositories.
 *


 * @since 2.0.0
 * @see EnableReactiveCassandraRepositories
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ReactiveSession.class, ReactiveCassandraRepository.class })
@ConditionalOnRepositoryType(store = "cassandra", type = RepositoryType.REACTIVE)
@ConditionalOnMissingBean(ReactiveCassandraRepositoryFactoryBean.class)
@Import(CassandraReactiveRepositoriesRegistrar.class)
@AutoConfigureAfter(CassandraReactiveDataAutoConfiguration.class)
public class CassandraReactiveRepositoriesAutoConfiguration {

}
