package org.springframework.boot.autoconfigure.data.neo4j.country;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CountryRepository extends Neo4jRepository<Country, Long> {

}
