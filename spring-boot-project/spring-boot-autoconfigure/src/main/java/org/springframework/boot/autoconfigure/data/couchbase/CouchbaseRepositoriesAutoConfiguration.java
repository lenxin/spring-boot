package org.springframework.boot.autoconfigure.data.couchbase;

import com.couchbase.client.java.Bucket;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;
import org.springframework.data.couchbase.repository.support.CouchbaseRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Couchbase
 * Repositories.
 *


 * @since 1.4.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Bucket.class, CouchbaseRepository.class })
@ConditionalOnBean(RepositoryOperationsMapping.class)
@ConditionalOnRepositoryType(store = "couchbase", type = RepositoryType.IMPERATIVE)
@ConditionalOnMissingBean(CouchbaseRepositoryFactoryBean.class)
@Import(CouchbaseRepositoriesRegistrar.class)
public class CouchbaseRepositoriesAutoConfiguration {

}
