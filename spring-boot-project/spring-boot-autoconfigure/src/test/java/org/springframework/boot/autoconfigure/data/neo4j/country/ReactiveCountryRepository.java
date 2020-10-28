package org.springframework.boot.autoconfigure.data.neo4j.country;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface ReactiveCountryRepository extends ReactiveNeo4jRepository<Country, Long> {

}
