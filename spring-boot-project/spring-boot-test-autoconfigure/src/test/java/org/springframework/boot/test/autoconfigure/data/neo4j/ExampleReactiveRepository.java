package org.springframework.boot.test.autoconfigure.data.neo4j;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

/**
 * Example reactive repository used with {@link DataNeo4jTest @DataNeo4jTest} tests.
 *

 */
interface ExampleReactiveRepository extends ReactiveNeo4jRepository<ExampleGraph, Long> {

}
