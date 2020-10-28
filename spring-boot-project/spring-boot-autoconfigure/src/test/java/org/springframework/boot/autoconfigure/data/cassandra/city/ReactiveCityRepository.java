package org.springframework.boot.autoconfigure.data.cassandra.city;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveCityRepository extends ReactiveCrudRepository<City, Long> {

}
