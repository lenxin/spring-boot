package org.springframework.boot.autoconfigure.data.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.cassandra.repository.support.CassandraRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Cassandra
 * Repositories.
 *

 * @see EnableCassandraRepositories
 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ CqlSession.class, CassandraRepository.class })
@ConditionalOnRepositoryType(store = "cassandra", type = RepositoryType.IMPERATIVE)
@ConditionalOnMissingBean(CassandraRepositoryFactoryBean.class)
@Import(CassandraRepositoriesRegistrar.class)
public class CassandraRepositoriesAutoConfiguration {

}
