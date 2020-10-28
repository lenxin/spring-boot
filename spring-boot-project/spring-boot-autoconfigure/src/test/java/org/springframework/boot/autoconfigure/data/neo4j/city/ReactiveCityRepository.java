package org.springframework.boot.autoconfigure.data.neo4j.city;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface ReactiveCityRepository extends ReactiveNeo4jRepository<City, Long> {

}
