package org.springframework.boot.autoconfigure.data.neo4j.city;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CityRepository extends Neo4jRepository<City, Long> {

	@Override
	Page<City> findAll(Pageable pageable);

}
