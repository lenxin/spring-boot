package org.springframework.boot.autoconfigure.data.elasticsearch.city;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveCityRepository extends ReactiveCrudRepository<City, String> {

}
