package org.springframework.boot.actuate.autoconfigure.neo4j;

import org.neo4j.driver.Driver;

import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.neo4j.Neo4jHealthContributorConfigurations.Neo4jConfiguration;
import org.springframework.boot.actuate.autoconfigure.neo4j.Neo4jHealthContributorConfigurations.Neo4jReactiveConfiguration;
import org.springframework.boot.actuate.neo4j.Neo4jHealthIndicator;
import org.springframework.boot.actuate.neo4j.Neo4jReactiveHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link Neo4jReactiveHealthIndicator} and {@link Neo4jHealthIndicator}.
 *



 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Driver.class)
@ConditionalOnBean(Driver.class)
@ConditionalOnEnabledHealthIndicator("neo4j")
@AutoConfigureAfter(Neo4jAutoConfiguration.class)
@Import({ Neo4jReactiveConfiguration.class, Neo4jConfiguration.class })
public class Neo4jHealthContributorAutoConfiguration {

}
