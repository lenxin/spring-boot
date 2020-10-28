package org.springframework.boot.autoconfigure.data.alt.cassandra;

import org.springframework.boot.autoconfigure.data.cassandra.city.City;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveCityCassandraRepository extends ReactiveCrudRepository<City, Long> {

}
