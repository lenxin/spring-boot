package org.springframework.boot.autoconfigure.data.couchbase;

import com.couchbase.client.java.Cluster;
import reactor.core.publisher.Flux;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Reactive Couchbase
 * support.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Cluster.class, ReactiveCouchbaseRepository.class, Flux.class })
@AutoConfigureAfter(CouchbaseDataAutoConfiguration.class)
@Import(CouchbaseReactiveDataConfiguration.class)
public class CouchbaseReactiveDataAutoConfiguration {

}
