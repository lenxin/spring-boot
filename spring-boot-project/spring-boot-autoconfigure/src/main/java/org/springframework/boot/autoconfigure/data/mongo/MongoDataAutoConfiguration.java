package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.client.MongoClient;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's mongo support.
 * <p>
 * Registers a {@link MongoTemplate} and {@link GridFsTemplate} beans if no other beans of
 * the same type are configured.
 * <p>
 * Honors the {@literal spring.data.mongodb.database} property if set, otherwise connects
 * to the {@literal test} database.
 *







 * @since 1.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ MongoClient.class, MongoTemplate.class })
@EnableConfigurationProperties(MongoProperties.class)
@Import({ MongoDataConfiguration.class, MongoDatabaseFactoryConfiguration.class,
		MongoDatabaseFactoryDependentConfiguration.class })
@AutoConfigureAfter(MongoAutoConfiguration.class)
public class MongoDataAutoConfiguration {

}
