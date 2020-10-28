package org.springframework.boot.autoconfigure.data.couchbase;

import com.couchbase.client.java.Cluster;
import reactor.core.publisher.Flux;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.data.couchbase.repository.config.ReactiveRepositoryOperationsMapping;
import org.springframework.data.couchbase.repository.support.ReactiveCouchbaseRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Couchbase Reactive
 * Repositories.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Cluster.class, ReactiveCouchbaseRepository.class, Flux.class })
@ConditionalOnRepositoryType(store = "couchbase", type = RepositoryType.REACTIVE)
@ConditionalOnBean(ReactiveRepositoryOperationsMapping.class)
@ConditionalOnMissingBean(ReactiveCouchbaseRepositoryFactoryBean.class)
@Import(CouchbaseReactiveRepositoriesRegistrar.class)
@AutoConfigureAfter(CouchbaseReactiveDataAutoConfiguration.class)
public class CouchbaseReactiveRepositoriesAutoConfiguration {

}
