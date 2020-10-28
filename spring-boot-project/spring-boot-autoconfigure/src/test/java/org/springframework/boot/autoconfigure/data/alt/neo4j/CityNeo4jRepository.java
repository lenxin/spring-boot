package org.springframework.boot.autoconfigure.data.alt.neo4j;

import org.springframework.boot.autoconfigure.data.neo4j.city.City;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CityNeo4jRepository extends Neo4jRepository<City, Long> {

}
